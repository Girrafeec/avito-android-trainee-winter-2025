package com.girrafeec.avito_deezer.ui.screen.player

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import com.forasoft.androidutils.ui.compose.effect.LifecycleEventObserver
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.ScreenOpened

@Composable
fun PlayerScreenBehavior(
    onEvent: (Event) -> Unit,
    onHidePlayerClicked: () -> Unit,
) {
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> onEvent(ScreenOpened)
            else -> Unit
        }
    }
}
