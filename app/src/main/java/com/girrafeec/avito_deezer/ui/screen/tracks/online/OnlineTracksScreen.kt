package com.girrafeec.avito_deezer.ui.screen.tracks.online

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreen
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme

@Composable
fun OnlineTracksScreen() {
    val viewModel = hiltViewModel<OnlineTracksViewModel>()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    OnlineTracksScreenContent(
        searchQuery = searchQuery,
        tracks = tracks,
        onEvent = remember { { viewModel.onEvent(it) } }
    )
}

@Composable
private fun OnlineTracksScreenContent(
    searchQuery: String,
    tracks: List<Track>,
    onEvent: (Event) -> Unit,
) {
    TracksScreen(
        searchQuery = searchQuery,
        tracks = tracks,
        onEvent = onEvent,
    )
}

@Composable
@Preview
fun OnlineTracksScreenPreview() {
    AvitoDeezerTheme {
        OnlineTracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            onEvent = {}
        )
    }
}
