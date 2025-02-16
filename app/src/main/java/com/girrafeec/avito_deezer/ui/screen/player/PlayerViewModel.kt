package com.girrafeec.avito_deezer.ui.screen.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.domain.TrackSource
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.KeyTrackId
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.KeyTrackSource
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.TrackIdDefaultValue
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.JumpToNextTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.JumpToPrevTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.PlaybackToggled
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.ScreenOpened
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: PlayerInteractor,
) : ViewModel() {

    private val trackId: Long? = getTrackId()
    private val trackSource: TrackSource = getTrackSource()

    private var currentTrackList: List<Track> = emptyList()

    private val _trackFlow = MutableStateFlow<Track?>(null)
    val trackFlow: StateFlow<Track?> = _trackFlow

    val playerStateValuesHolder = interactor.playerStateValuesHolder

    init {
        collectTrackList()
    }

    fun onEvent(event: Event) {
        Timber.tag(TAG).v("event: $event")
        when (event) {
            ScreenOpened -> onScreenOpened()
            PlaybackToggled -> onPlaybackToggled()
            JumpToNextTrackClicked -> onJumpToNextTrackClicked()
            JumpToPrevTrackClicked -> onJumpToPrevTrackClicked()
        }
    }

    private fun onScreenOpened() {

    }

    private fun onPlaybackToggled() {

    }

    private fun onJumpToNextTrackClicked() {

    }

    private fun onJumpToPrevTrackClicked() {

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
    }

    companion object {
        private const val TAG = "PlayerViewModel"
    }
}
