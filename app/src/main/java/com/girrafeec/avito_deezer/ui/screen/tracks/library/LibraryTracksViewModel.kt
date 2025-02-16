package com.girrafeec.avito_deezer.ui.screen.tracks.library

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect.ShowPlayer
import com.girrafeec.avito_deezer.usecase.library.SearchLibraryTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

// TODO: [High] Check permission via PermissionManager
@HiltViewModel
class LibraryTracksViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: LibraryTracksInteractor,
    @ApplicationContext
    private val context: Context,
) : BaseTracksViewModel() {

    val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow(key = KEY_SEARCH_QUERY, initialValue = "")

    private val libraryTracksFlow = combine(interactor.getLibraryTracks()) {
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
        observeLibraryTracks()
        observeSearchQuery()
        if (isMediaAudioPermissionGranted()) loadTracks()
    }

    override fun onScreenOpened() {
        if (!isMediaAudioPermissionGranted()) {
            emitSideEffect(SideEffect.ShowMediaAudioPermission)
        }
    }

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
            interactor.fetchLibraryTracks()
                .onFailure {
                    Timber.tag(TAG).e(it)
                }
        }
    }

    override fun searchForTracks(searchQuery: String) {
        if (searchTracksJob?.isActive == true) return
        searchTracksJob = viewModelScope.launch {
            _tracks.value = emptyList()
            val params = SearchLibraryTracksUseCase.Params(searchQuery = searchQuery)
            interactor.searchLibraryTracks(params)
                .onSuccess {
                    _tracks.value = it
                }
                .onFailure {
                    Timber.tag(TAG).e(it)
                }
        }
    }

    // TODO: [High] Reuse
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchQuery
            .debounce(SEARCH_QUERY_TIMEOUT)
            .onEach { searchQuery ->
                if (searchQuery.isBlank()) {
                    _tracks.value = libraryTracksFlow.value
                } else {
                    searchForTracks(searchQuery)
                }
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }

    private fun observeLibraryTracks() {
        libraryTracksFlow
            .onEach {
                _tracks.value = it
            }
            .launchIn(viewModelScope + Dispatchers.Default)
    }

    private fun isMediaAudioPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO) ==
                PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val TAG = "LibraryTracksViewModel"

        private const val SEARCH_QUERY_TIMEOUT = 1000L
    }
}
