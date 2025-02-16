package com.girrafeec.avito_deezer.ui.screen.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme

@Composable
fun PlayerScreen(
    onHidePlayerClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<PlayerViewModel>()

    PlayerScreenContent(
        onHidePlayerClicked = onHidePlayerClicked,
        onEvent = remember { { viewModel.onEvent(it) } }
    )
}

@Composable
fun PlayerScreenContent(
    onHidePlayerClicked: () -> Unit,
    onEvent: (Event) -> Unit,
) {
    PlayerScreenBehavior(
        onHidePlayerClicked = onHidePlayerClicked,
        onEvent = onEvent,
    )
}

@Composable
@Preview
fun PlayerScreenPreview() {
    AvitoDeezerTheme {
        PlayerScreenContent(
            onEvent = {},
            onHidePlayerClicked = {},
        )
    }
}
