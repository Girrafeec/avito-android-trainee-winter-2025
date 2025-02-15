package com.girrafeec.avito_deezer.ui.screen.tracks.common

import androidx.lifecycle.ViewModel
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.OnSearchQueryEntered
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.ScreenOpened

abstract class BaseTracksViewModel : ViewModel() {
    protected abstract fun loadTracks()

    protected abstract fun searchForTracks()

    fun onEvent(event: Event) {
        when (event) {
            ScreenOpened -> onScreenOpened()
            is OnSearchQueryEntered -> onSearchQueryEntered(event.searchQuery)
            is Event.OnTrackClicked -> onTrackClicked(event.track)
        }
    }

    protected abstract fun onScreenOpened()

    protected abstract fun onSearchQueryEntered(searchQuery: String)

    protected abstract fun onTrackClicked(track: Track)

    sealed interface Event {
        data object ScreenOpened : Event
        data class OnSearchQueryEntered(val searchQuery: String) : Event
        data class OnTrackClicked(val track: Track) : Event
    }
}
