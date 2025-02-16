package com.girrafeec.avito_deezer.ui.screen.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.base.SideEffectManager
import com.girrafeec.avito_deezer.base.SideEffectManagerImpl
import com.girrafeec.avito_deezer.component.player.MediaSource
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.domain.TrackSource
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.KeyTrackId
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.KeyTrackSource
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.TrackIdDefaultValue
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.JumpToNextTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.JumpToPrevTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.PlaybackSeek
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.PlaybackToggled
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.ScreenOpened
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.SideEffect
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.SideEffect.LaunchBackgroundPlayback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: PlayerInteractor,
) : ViewModel(), SideEffectManager<SideEffect> by SideEffectManagerImpl() {

    private val trackId: Long? = getTrackId()
    private val trackSource: TrackSource = getTrackSource()

    private var currentTrackList: List<Track> = emptyList()

    private val _trackFlow = MutableStateFlow<Track?>(null)
    val trackFlow: StateFlow<Track?> = _trackFlow

    val playerStateValuesHolder = interactor.playerStateValuesHolder

    init {
        collectTrackList()
        observeTrack()
    }

    fun onEvent(event: Event) {
        Timber.tag(TAG).v("event: $event")
        when (event) {
            ScreenOpened -> onScreenOpened()
            PlaybackToggled -> onPlaybackToggled()
            JumpToNextTrackClicked -> onJumpToNextTrackClicked()
            JumpToPrevTrackClicked -> onJumpToPrevTrackClicked()
            is PlaybackSeek -> onPlaybackSeek(event.progress)
        }
    }

    private fun onScreenOpened() {}

    private fun onPlaybackToggled() {
        interactor.togglePlayback()
    }

    private fun onJumpToNextTrackClicked() {

    }

    private fun onJumpToPrevTrackClicked() {

    }

    private fun onPlaybackSeek(progress: Float) {
        interactor.seekToPosition(progress)
    }

    private fun collectTrackList() {
        viewModelScope.launch {
            interactor.onlineTracksFlow.collect { tracks ->
                if (trackSource == TrackSource.ONLINE) currentTrackList = tracks
                _trackFlow.value = currentTrackList.find { it.id == trackId }
            }
        }

        viewModelScope.launch {
            interactor.libraryTracksFlow.collect { tracks ->
                if (trackSource == TrackSource.LIBRARY) currentTrackList = tracks
                _trackFlow.value = currentTrackList.find { it.id == trackId }
            }
        }
    }

    private fun observeTrack() {
        trackFlow
            .filterNotNull()
            .onEach { track ->
                // TODO: [High] Move to separate method
                val mediaSource = MediaSource(
                    uri = track.trackUri ?: track.trackUrl ?: error("track uri is null"),
                    source = trackSource
                )
                interactor.setMediaSource(mediaSource)
                interactor.preparePlayer()
                interactor.play()
                emitSideEffect(LaunchBackgroundPlayback)
            }
            .launchIn(viewModelScope + Dispatchers.Default)
    }

    private fun getTrackId(): Long? {
        val trackId =
            savedStateHandle.get<Long>(KeyTrackId) ?: error("trackId is null")
        return if (trackId != TrackIdDefaultValue) trackId else null
    }

    private fun getTrackSource(): TrackSource {
        val source =
            savedStateHandle.get<TrackSource>(KeyTrackSource) ?: error("trackSource is null")
        return source
    }

    sealed interface Event {
        data object ScreenOpened : Event
        data object PlaybackToggled : Event
        data object JumpToNextTrackClicked : Event
        data object JumpToPrevTrackClicked : Event
        data class PlaybackSeek(val progress: Float) : Event
    }

    sealed interface SideEffect {
        data object LaunchBackgroundPlayback : SideEffect
    }

    companion object {
        private const val TAG = "PlayerViewModel"
    }
}
