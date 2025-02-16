package com.girrafeec.avito_deezer.ui.screen.player.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.girrafeec.avito_deezer.domain.PlaybackState
import timber.log.Timber
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Stable
interface PlayerState {
    val playbackState: PlaybackState
    val playbackPositionMillis: Long
    val playbackProgress: Float
    val duration: Duration
}

@Stable
class PlayerStateImpl(
    playbackStateState: State<PlaybackState>,
    playbackPositionMillisState: State<Long>,
    playbackProgressState: State<Float>,
    durationState: State<Duration>,
) : PlayerState {
    override val playbackState by playbackStateState
    override val playbackPositionMillis by playbackPositionMillisState
    override val playbackProgress by playbackProgressState
    override val duration by durationState
}

@Immutable
class NoopPlayerState : PlayerState {
    override val playbackState = PlaybackState.NOT_PLAYING
    override val playbackPositionMillis = 0L
    override val playbackProgress = 0f
    override val duration = 1.minutes
}

@Composable
fun rememberPlayerState(playerStateValuesHolder: PlayerStateValuesHolder): PlayerState {
    val playbackStateState = playerStateValuesHolder.playbackState.collectAsStateWithLifecycle()
    val playbackPositionMillisState =
        playerStateValuesHolder.playbackPositionMillis.collectAsStateWithLifecycle()
    val playbackProgressState =
        playerStateValuesHolder.playbackProgress.collectAsStateWithLifecycle()
    val durationState = playerStateValuesHolder.duration.collectAsStateWithLifecycle()

    return remember(
        playbackStateState,
        playbackPositionMillisState,
        playbackProgressState,
        durationState,
    ) {
        PlayerStateImpl(
            playbackStateState = playbackStateState,
            playbackPositionMillisState = playbackPositionMillisState,
            playbackProgressState = playbackProgressState,
            durationState = durationState,
        ).also { Timber.v("PlayerState created") }
    }
}
