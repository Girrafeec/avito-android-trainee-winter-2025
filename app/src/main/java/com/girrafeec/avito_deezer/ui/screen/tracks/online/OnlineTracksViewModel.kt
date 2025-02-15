package com.girrafeec.avito_deezer.ui.screen.tracks.online

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnlineTracksViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: OnlineTracksInteractor,
) : BaseTracksViewModel() {

    val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow(key = KEY_SEARCH_QUERY, initialValue = "")

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks = _tracks.asStateFlow()

    private var loadTracksJob: Job? = null

    init {
        loadTracks()
    }

    // TODO: [High] Implement Side Effects for it
    override fun onScreenOpened() {
        loadTracks()
    }

    // TODO: [High] Search with some delay
    override fun onSearchQueryEntered(searchQuery: String) {
        searchForTracks()
    }

    override fun onTrackClicked(track: Track) {
        TODO("Not yet implemented")
    }

    override fun loadTracks() {
        if (loadTracksJob?.isActive == true) return
        loadTracksJob = viewModelScope.launch {
            interactor.getOnlineTracks()
                .onSuccess {
                    _tracks.value = it
                }
                .onFailure {
                    Timber.tag(TAG).e(it)
                }
        }
    }

    override fun searchForTracks() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val TAG = "OnlineTracksViewModel"
    }
}
