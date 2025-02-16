package com.girrafeec.avito_deezer.ui.screen.tracks.online

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect.ShowPlayer
import com.girrafeec.avito_deezer.usecase.online.SearchOnlineTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnlineTracksViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: OnlineTracksInteractor,
) : BaseTracksViewModel() {

    val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow(key = KEY_SEARCH_QUERY, initialValue = "")

    private val onlineTracksFlow = combine(interactor.getOnlineTracks()) {
        val result = it[0]
        result.getOrDefault(emptyList())
    }.stateIn(
        scope = viewModelScope + Dispatchers.Default,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(),
    )

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks = _tracks.asStateFlow()

    private var loadTracksJob: Job? = null
    private var searchTracksJob: Job? = null

    init {
        observeOnlineTracks()
        observeSearchQuery()
        loadTracks()
    }

    override fun onScreenOpened() {}

    // TODO: [Medium priority] Add some validation
    override fun onSearchQueryEntered(searchQuery: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = searchQuery
    }

    override fun onTrackClicked(track: Track) {
        emitSideEffect(ShowPlayer(track))
    }

    override fun loadTracks() {
        if (loadTracksJob?.isActive == true) return
        loadTracksJob = viewModelScope.launch {
            interactor.fetchOnlineTracks()
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
            interactor.searchOnlineTracks(params)
                .onSuccess {
                    _tracks.value = it
                }
                .onFailure {
                    Timber.tag(TAG).e(it)
                }
        }
    }

    // TODO: [Medium priority] Reuse
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchQuery
            .debounce(SEARCH_QUERY_TIMEOUT)
            .onEach { searchQuery ->
                if (searchQuery.isBlank()) {
                    _tracks.value = onlineTracksFlow.value
                } else {
                    searchForTracks(searchQuery)
                }
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }

    private fun observeOnlineTracks() {
        onlineTracksFlow
            .onEach {
                _tracks.value = it
            }
            .launchIn(viewModelScope + Dispatchers.Default)
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val TAG = "OnlineTracksViewModel"

        private const val SEARCH_QUERY_TIMEOUT = 1000L
    }
}
