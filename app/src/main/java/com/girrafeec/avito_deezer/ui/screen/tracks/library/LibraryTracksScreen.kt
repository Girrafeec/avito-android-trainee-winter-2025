package com.girrafeec.avito_deezer.ui.screen.tracks.library

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreen
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme

@Composable
fun LibraryTracksScreen() {

}

@Composable
private fun LibraryTracksScreenContent(
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
fun LibraryTracksScreenPreview() {
    AvitoDeezerTheme {
        LibraryTracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            onEvent = {}
        )
    }
}
