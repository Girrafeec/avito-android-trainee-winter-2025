package com.girrafeec.avito_deezer.ui.screen.tracks.online

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect
import com.girrafeec.avito_deezer.ui.screen.tracks.common.TracksScreen
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme
import com.girrafeec.avito_deezer.util.NoopPermissionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun OnlineTracksScreen(
    onShowPlayer: (Track) -> Unit,
) {
    val viewModel = hiltViewModel<OnlineTracksViewModel>()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    OnlineTracksScreenContent(
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = viewModel.sideEffects,
        onEvent = remember { { viewModel.onEvent(it) } },
        onShowPlayer = onShowPlayer,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun OnlineTracksScreenContent(
    searchQuery: String,
    tracks: List<Track>,
    sideEffects: Flow<SideEffect>,
    onEvent: (Event) -> Unit,
    onShowPlayer: (Track) -> Unit,
) {
    TracksScreen(
        mediaAudioPermissionState = NoopPermissionState(),
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = sideEffects,
        onEvent = onEvent,
        onShowPlayer = onShowPlayer
    )
}

@Composable
@Preview
fun OnlineTracksScreenPreview() {
    AvitoDeezerTheme {
        OnlineTracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            sideEffects = emptyFlow(),
            onEvent = {},
            onShowPlayer = {},
        )
    }
}
