package com.girrafeec.avito_deezer.ui.screen.tracks.library

import android.Manifest
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
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LibraryTracksScreen(
    onShowPlayer: (Track) -> Unit,
) {
    val viewModel = hiltViewModel<LibraryTracksViewModel>()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    val mediaAudioPermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_MEDIA_AUDIO
    ) { permissionResult ->
        Timber.v("Read media audio permission granted: $permissionResult")
    }

    LibraryTracksScreenContent(
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = viewModel.sideEffects,
        mediaAudioPermissionState = mediaAudioPermissionState,
        onEvent = remember { { viewModel.onEvent(it) } },
        onShowPlayer = onShowPlayer,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun LibraryTracksScreenContent(
    searchQuery: String,
    tracks: List<Track>,
    mediaAudioPermissionState: PermissionState,
    sideEffects: Flow<SideEffect>,
    onShowPlayer: (Track) -> Unit,
    onEvent: (Event) -> Unit,
) {
    TracksScreen(
        searchQuery = searchQuery,
        tracks = tracks,
        sideEffects = sideEffects,
        mediaAudioPermissionState = mediaAudioPermissionState,
        onShowPlayer = onShowPlayer,
        onEvent = onEvent,
        isLocal = true,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
fun LibraryTracksScreenPreview() {
    AvitoDeezerTheme {
        LibraryTracksScreenContent(
            searchQuery = "",
            tracks = emptyList(),
            sideEffects = emptyFlow(),
            mediaAudioPermissionState = NoopPermissionState(),
            onEvent = {},
            onShowPlayer = {},
        )
    }
}
