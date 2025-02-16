package com.girrafeec.avito_deezer.ui.screen.tracks.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.girrafeec.avito_deezer.R
import com.girrafeec.avito_deezer.domain.Album
import com.girrafeec.avito_deezer.domain.Artist
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.OnSearchQueryEntered
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.OnTrackClicked
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreenComponents.Tracks
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreenComponents.TracksSearchField
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme

// TODO: [High] Rename isLocal param
@Composable
fun TracksScreen(
    searchQuery: String,
    tracks: List<Track>,
    onEvent: (Event) -> Unit,
    isLocal: Boolean = true,
) {
    TracksScreenContent(
        searchQuery = searchQuery,
        tracks = tracks,
        onEvent = onEvent,
        isLocal = isLocal,
    )
}

// TODO: [Medium priority] Show no tracks placeholder
@Composable
private fun TracksScreenContent(
    searchQuery: String,
    tracks: List<Track>,
    onEvent: (Event) -> Unit,
    isLocal: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        TracksSearchField(
            searchQuery = searchQuery,
            onSearchInputChanged = { onEvent(OnSearchQueryEntered(it)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        // TODO: [High] Show text on boolean flag
        Text(
            text = stringResource(R.string.screen_tracks_chart_title),
            style = UiKitTheme.typography.heading.xl,
            color = UiKitTheme.colors.text.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Tracks(
            tracks = tracks,
            onTrackClicked = { onEvent(OnTrackClicked(it)) }
        )
    }
}

@Composable
@Preview
private fun TracksScreenPreview() {
    AvitoDeezerTheme {
        TracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            onEvent = {},
            isLocal = true
        )
    }
}

@Composable
@Preview
private fun TracksScreenQueryPreview() {
    AvitoDeezerTheme {
        TracksScreenContent(
            searchQuery = "AC/DC",
            tracks = emptyList(),
            onEvent = {},
            isLocal = true
        )
    }
}

@Composable
@Preview
private fun TracksScreenTrackListPreview() {
    AvitoDeezerTheme {
        TracksScreenContent(
            searchQuery = "AC/DC",
            tracks = listOf(
                Track(
                    id = 1,
                    title = "Song 1",
                    duration = 134,
                    artist = Artist(
                        name = "Artist 1"
                    ),
                    album = Album(
                        title = "Album 1"
                    ),
                ),
                Track(
                    id = 2,
                    title = "Song 2",
                    duration = 169,
                    artist = Artist(
                        name = "Artist 2"
                    ),
                    album = Album(
                        title = "Album 2"
                    ),
                ),
                Track(
                    id = 3,
                    title = "Song 3",
                    duration = 178,
                    artist = Artist(
                        name = "Artist 3"
                    ),
                    album = Album(
                        title = "Album 3"
                    ),
                ),
            ),
            onEvent = {},
            isLocal = true
        )
    }
}
