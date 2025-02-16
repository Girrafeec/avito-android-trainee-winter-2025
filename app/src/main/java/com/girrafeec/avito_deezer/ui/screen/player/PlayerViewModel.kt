package com.girrafeec.avito_deezer.ui.screen.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.domain.TrackSource
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.KeyTrackId
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.KeyTrackSource
import com.girrafeec.avito_deezer.ui.navigation.Destinations.PlayerDestination.TrackIdDefaultValue
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.NextTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.PlaybackToggled
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.PreviousTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.ScreenOpened
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        Timber.tag(TAG).v("trackId: $trackId")
        collectTrackList()
    }

    fun onEvent(event: Event) {
        Timber.tag(TAG).v("event: $event")
        when (event) {
            ScreenOpened -> onScreenOpened()
            PlaybackToggled -> onPlaybackToggled()
            NextTrackClicked -> onNextTrackClicked()
            PreviousTrackClicked -> onPreviousTrackClicked()
        }
    }

    private fun onScreenOpened() {

    }

    private fun onPlaybackToggled() {

    }

    private fun onNextTrackClicked() {

    }

    private fun onPreviousTrackClicked() {

    }

    private fun collectTrackList() {
        viewModelScope.launch {
            interactor.onlineTracksFlow.collect { tracks ->
                if (trackSource == TrackSource.ONLINE) currentTrackList = tracks
                Timber.tag(TAG).v("tracks: $currentTrackList")
            }
        }

        viewModelScope.launch {
            interactor.libraryTracksFlow.collect { tracks ->
                if (trackSource == TrackSource.LIBRARY) currentTrackList = tracks
                Timber.tag(TAG).v("tracks: $currentTrackList")
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
        data object NextTrackClicked : Event
        data object PreviousTrackClicked : Event
    }

    companion object {
        private const val TAG = "PlayerViewModel"
    }
}
