package com.girrafeec.avito_deezer.ui.screen.tracks.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreen
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LibraryTracksScreen(
    onShowPlayer: (Track) -> Unit,
) {
    val viewModel = hiltViewModel<LibraryTracksViewModel>()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    LibraryTracksScreenContent(
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = viewModel.sideEffects,
        onEvent = remember { { viewModel.onEvent(it) } },
        onShowPlayer = onShowPlayer,
    )
}

@Composable
private fun LibraryTracksScreenContent(
    searchQuery: String,
    tracks: List<Track>,
    sideEffects: Flow<SideEffect>,
    onShowPlayer: (Track) -> Unit,
    onEvent: (Event) -> Unit,
) {
    TracksScreen(
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = sideEffects,
        onShowPlayer = onShowPlayer,
        onEvent = onEvent,
    )
}

@Composable
@Preview
fun LibraryTracksScreenPreview() {
    AvitoDeezerTheme {
        LibraryTracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            sideEffects = emptyFlow(),
            onEvent = {},
            onShowPlayer = {},
        )
    }
}
