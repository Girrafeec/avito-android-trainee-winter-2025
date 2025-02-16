package com.girrafeec.avito_deezer.ui.screen.tracks.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel
import com.girrafeec.avito_deezer.usecase.online.SearchOnlineTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LibraryTracksViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: LibraryTracksInteractor,
) : BaseTracksViewModel() {

    val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow(key = KEY_SEARCH_QUERY, initialValue = "")

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks = _tracks.asStateFlow()

    private var loadTracksJob: Job? = null
    private var searchTracksJob: Job? = null

    init {
        loadTracks()
        observeSearchQuery()
    }

    // TODO: [High] Implement Side Effects for it
    override fun onScreenOpened() {
        loadTracks()
    }

    // TODO: [Medium priority] Add some validation
    override fun onSearchQueryEntered(searchQuery: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = searchQuery
    }

    override fun onTrackClicked(track: Track) {
        TODO("Not yet implemented")
    }

    override fun loadTracks() {
        if (loadTracksJob?.isActive == true) return
        loadTracksJob = viewModelScope.launch {
            interactor.getLibraryTracks()
                .onSuccess {
                    _tracks.value = it
                }
                .onFailure {
                    Timber.tag(TAG).e(it)
                }
        }
    }

    override fun searchForTracks(searchQuery: String) {
        if (searchTracksJob?.isActive == true) return
        searchTracksJob = viewModelScope.launch {
            _tracks.value = emptyList()
            val params = SearchOnlineTracksUseCase.Params(searchQuery = searchQuery)
//            interactor.searchOnlineTracks(params)
//                .onSuccess {
//                    _tracks.value = it
//                }
//                .onFailure {
//                    Timber.tag(TAG).e(it)
//                }
        }
    }

    // TODO: [High] Reuse
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchQuery
            .filterNot { it.isBlank() }
            .debounce(SEARCH_QUERY_TIMEOUT)
            .onEach { searchQuery ->
                searchForTracks(searchQuery)
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val TAG = "OnlineTracksViewModel"

        private const val SEARCH_QUERY_TIMEOUT = 1000L
    }
}
