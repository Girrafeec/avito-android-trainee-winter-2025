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
import com.girrafeec.avito_deezer.domain.TrackSource
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.OnSearchQueryEntered
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.OnTrackClicked
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreenComponents.Tracks
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreenComponents.TracksSearchField
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme
import com.girrafeec.avito_deezer.util.NoopPermissionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.time.Duration.Companion.seconds

// TODO: [High] Rename isLocal param
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TracksScreen(
    searchQuery: String,
    tracks: List<Track>,
    sideEffects: Flow<SideEffect>,
    mediaAudioPermissionState: PermissionState,
    onEvent: (Event) -> Unit,
    onShowPlayer: (Track) -> Unit,
    isLocal: Boolean = false,
) {
    TracksScreenContent(
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = sideEffects,
        mediaAudioPermissionState = mediaAudioPermissionState,
        onShowPlayer = onShowPlayer,
        onEvent = onEvent,
        isLocal = isLocal,
    )
}

// TODO: [Medium priority] Show no tracks placeholder
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TracksScreenContent(
    searchQuery: String,
    tracks: List<Track>,
    sideEffects: Flow<SideEffect>,
    mediaAudioPermissionState: PermissionState,
    onShowPlayer: (Track) -> Unit,
    onEvent: (Event) -> Unit,
    isLocal: Boolean
) {
    TrackScreenBehavior(
        sideEffects = sideEffects,
        onEvent = onEvent,
        mediaAudioPermissionState = mediaAudioPermissionState,
        onShowPlayer = onShowPlayer,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, start = 16.dp, top = 24.dp, bottom = 4.dp)
    ) {
        TracksSearchField(
            searchQuery = searchQuery,
            onSearchInputChanged = { onEvent(OnSearchQueryEntered(it)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        val stringResId = if (isLocal) {
            R.string.screen_tracks_library_title
        } else {
            R.string.screen_tracks_chart_title
        }
        Text(
            text = stringResource(stringResId),
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
private fun TracksScreenPreview() {
    AvitoDeezerTheme {
        TracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            sideEffects = emptyFlow(),
            onEvent = {},
            onShowPlayer = {},
            isLocal = false,
            mediaAudioPermissionState = NoopPermissionState(),
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
private fun TracksScreenQueryPreview() {
    AvitoDeezerTheme {
        TracksScreenContent(
            searchQuery = "AC/DC",
            tracks = emptyList(),
            sideEffects = emptyFlow(),
            onEvent = {},
            onShowPlayer = {},
            isLocal = false,
            mediaAudioPermissionState = NoopPermissionState(),
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
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
                    duration = 134.seconds,
                    artist = Artist(
                        name = "Artist 1"
                    ),
                    album = Album(
                        title = "Album 1"
                    ),
                    trackSource = TrackSource.LIBRARY,
                ),
                Track(
                    id = 2,
                    title = "Song 2",
                    duration = 169.seconds,
                    artist = Artist(
                        name = "Artist 2"
                    ),
                    album = Album(
                        title = "Album 2"
                    ),
                    trackSource = TrackSource.LIBRARY,
                ),
                Track(
                    id = 3,
                    title = "Song 3",
                    duration = 178.seconds,
                    artist = Artist(
                        name = "Artist 3"
                    ),
                    album = Album(
                        title = "Album 3"
                    ),
                    trackSource = TrackSource.LIBRARY,
                ),
            ),
            sideEffects = emptyFlow(),
            onEvent = {},
            onShowPlayer = {},
            isLocal = false,
            mediaAudioPermissionState = NoopPermissionState(),
        )
    }
}
