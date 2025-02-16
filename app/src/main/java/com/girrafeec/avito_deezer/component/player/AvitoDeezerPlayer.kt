package com.girrafeec.avito_deezer.component.player

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.SystemClock
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.girrafeec.avito_deezer.component.AvitoDeezerContentResolver
import com.girrafeec.avito_deezer.domain.PlaybackState
import com.girrafeec.avito_deezer.domain.TrackSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class AvitoDeezerPlayer @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val exoPlayer: ExoPlayer,
    private val contentResolver: AvitoDeezerContentResolver,
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val playerHandler = exoPlayer.getHandler()
    private val playerDispatcher = playerHandler.asCoroutineDispatcher()
    private val playerScope = CoroutineScope(playerDispatcher)

    private val isPlaying: StateFlow<Boolean> = callbackFlow {
        send(exoPlayer.isPlaying)

        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                Timber.tag(TAG).v("onIsPlayingChanged: $isPlaying")
                trySend(isPlaying)
            }
        }
        exoPlayer.addListener(listener)

        awaitClose {
            exoPlayer.removeListener(listener)
        }
    }
        .flowOn(playerDispatcher)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    private val isLoading: StateFlow<Boolean> = callbackFlow {
        send(exoPlayer.isLoading)

        val listener = object : Player.Listener {
            override fun onIsLoadingChanged(isLoading: Boolean) {
                Timber.tag(TAG).v("onIsLoadingChanged: $isLoading")
                trySend(isLoading)
            }
        }
        exoPlayer.addListener(listener)

        awaitClose {
            exoPlayer.removeListener(listener)
        }
    }
        .flowOn(playerDispatcher)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    val playbackState: StateFlow<PlaybackState> = combine(
        isPlaying,
        isLoading,
    ) { isPlaying, isLoading ->
        when {
            isPlaying -> PlaybackState.PLAYING
            isLoading -> PlaybackState.LOADING
            else -> PlaybackState.NOT_PLAYING
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PlaybackState.NOT_PLAYING,
    )

    val duration: StateFlow<Duration> = callbackFlow {
        send(exoPlayer.duration.milliseconds)

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                trySend(exoPlayer.duration.milliseconds)
            }
        }
        exoPlayer.addListener(listener)

        awaitClose {
            exoPlayer.removeListener(listener)
        }
    }
        .flowOn(playerDispatcher)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DURATION_UNKNOWN,
        )

    private val _playbackPositionMillis = MutableStateFlow(0L)
    val playbackPositionMillis: StateFlow<Long> = _playbackPositionMillis.asStateFlow()

    val playbackProgress = combine(
        playbackPositionMillis,
        duration,
    ) { playerPositionMillis, duration ->
        getPlaybackProgress(playerPositionMillis, duration)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0f,
    )

    private val isPlaybackCompleted: Boolean
        get() = playbackPositionMillis.value >= duration.value.inWholeMilliseconds

    init {
        setUpPositionTracking()
    }

    fun setMedia(source: MediaSource) {
        playerScope.launch {
            exoPlayer.setMediaItem(createMediaItem(source))
        }
    }

    fun preparePlayer() {
        playerScope.launch {
            exoPlayer.prepare()
        }
    }

    fun play() {
        if (isPlaybackCompleted) {
            seekToStart()
        }
        playerScope.launch {
            exoPlayer.play()
        }
    }

    fun seek(progress: Float) {
        val coercedProgress = progress.coerceIn(0f, 1f)
        val positionMillis =
            (duration.value.inWholeMilliseconds * coercedProgress).roundToLong()
        seekToPosition(positionMillis)
    }

    fun togglePlayback() {
        if (isPlaying.value) pause() else play()
    }

    fun release() {
        playerScope.launch {
            exoPlayer.release()
        }
    }

    private fun pause() {
        playerScope.launch {
            exoPlayer.pause()
        }
    }

    private fun createMediaItem(source: MediaSource): MediaItem {
        val uri = when (source.source) {
            TrackSource.ONLINE -> source.uri.toUri()
            TrackSource.LIBRARY -> createUriFromFilePath(source.uri)
        } ?: error("uri does not exist")
        return MediaItem.fromUri(uri)
    }

    private fun createUriFromFilePath(path: String): Uri? {
        return contentResolver.getMusicFileUri(path)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setUpPositionTracking() {
        playbackState
            .transformLatest<PlaybackState, Unit> { playbackState ->
                if (playbackState.isPlaying) {
                    while (currentCoroutineContext().isActive) {
                        val startTimestampMillis = SystemClock.elapsedRealtime()
                        delay(PROGRESS_POLLING_DELAY)
                        val elapsedTimeMillis =
                            SystemClock.elapsedRealtime() - startTimestampMillis
                        val newPlaybackPositionMillis =
                            playbackPositionMillis.value + elapsedTimeMillis
                        updatePlaybackPosition(newPlaybackPositionMillis)
                    }
                }
            }
            .launchIn(coroutineScope + Dispatchers.Default)
    }

    private fun seekToStart() {
        playerScope.launch {
            exoPlayer.seekToDefaultPosition()
            updatePlaybackPosition(0L)
        }
    }

    private fun updatePlaybackPosition(positionMillis: Long) {
        val trackDurationMillis = duration.value.inWholeMilliseconds.coerceAtLeast(0L)
        val newPlaybackPositionMillis = positionMillis.coerceIn(0L, trackDurationMillis)
        _playbackPositionMillis.value = newPlaybackPositionMillis
    }

    private fun seekToPosition(positionMillis: Long) {
        playerScope.launch {
            val durationMillis = exoPlayer.duration.coerceAtLeast(0L)
            val coercedPositionMillis = positionMillis.coerceIn(0L, durationMillis)
            exoPlayer.seekTo(coercedPositionMillis)
            updatePlaybackPosition(positionMillis)
        }
    }

    private fun getPlaybackProgress(
        playbackPositionMillis: Long,
        duration: Duration,
    ): Float {
        return if (duration > Duration.ZERO) {
            playbackPositionMillis.toFloat() / duration.inWholeMilliseconds
        } else {
            0f
        }
    }

    companion object {
        private const val TAG = "AvitoDeezerPlayer"

        val DURATION_UNKNOWN = C.TIME_UNSET.milliseconds
        val PROGRESS_POLLING_DELAY = 50.milliseconds
    }
}

fun ExoPlayer.getHandler(): Handler {
    return Handler(this.applicationLooper)
}
