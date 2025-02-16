package com.girrafeec.avito_deezer.ui.screen.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.girrafeec.avito_deezer.domain.Album
import com.girrafeec.avito_deezer.domain.Artist
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.domain.TrackSource
import com.girrafeec.avito_deezer.ui.screen.player.PlayerScreenComponents.PlaybackControls
import com.girrafeec.avito_deezer.ui.screen.player.PlayerScreenComponents.PlaybackProgressSlider
import com.girrafeec.avito_deezer.ui.screen.player.PlayerScreenComponents.TopBar
import com.girrafeec.avito_deezer.ui.screen.player.PlayerScreenComponents.TrackCard
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.JumpToNextTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.JumpToPrevTrackClicked
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.PlaybackToggled
import com.girrafeec.avito_deezer.ui.screen.player.state.NoopPlayerState
import com.girrafeec.avito_deezer.ui.screen.player.state.PlayerState
import com.girrafeec.avito_deezer.ui.screen.player.state.rememberPlayerState
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme
import kotlin.time.Duration.Companion.seconds

@Composable
fun PlayerScreen(
    onHidePlayerClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<PlayerViewModel>()
    val track by viewModel.trackFlow.collectAsStateWithLifecycle()
    val playerState = rememberPlayerState(viewModel.playerStateValuesHolder)

    PlayerScreenContent(
        track = track,
        playerState = playerState,
        onHidePlayerClicked = onHidePlayerClicked,
        onEvent = remember { { viewModel.onEvent(it) } }
    )
}

@Composable
fun PlayerScreenContent(
    track: Track?,
    playerState: PlayerState,
    onHidePlayerClicked: () -> Unit,
    onEvent: (Event) -> Unit,
) {
    PlayerScreenBehavior(
        onHidePlayerClicked = onHidePlayerClicked,
        onEvent = onEvent,
    )

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (track != null) {
            TopBar(
                track = track,
                onHidePlayerClick = onHidePlayerClicked,
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        TrackCard()
        Spacer(modifier = Modifier.height(16.dp))
        PlaybackProgressSlider()
        Spacer(modifier = Modifier.height(64.dp))
        PlaybackControls(
            isPlaying = playerState.playbackState.isPlaying,
            onPlaybackToggled = { onEvent(PlaybackToggled) },
            onJumpToPrevTrackClicked = { onEvent(JumpToPrevTrackClicked) },
            onJumpToNextTrackClicked = { onEvent(JumpToNextTrackClicked) },
        )
    }
}

@Composable
@Preview
fun PlayerScreenPreview() {
    AvitoDeezerTheme {
        PlayerScreenContent(
            track = Track(
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
            playerState = NoopPlayerState(),
            onEvent = {},
            onHidePlayerClicked = {},
        )
    }
}
