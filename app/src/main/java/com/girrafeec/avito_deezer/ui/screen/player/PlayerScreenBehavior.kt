package com.girrafeec.avito_deezer.ui.screen.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import com.forasoft.androidutils.ui.compose.effect.LifecycleEventObserver
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.Event.ScreenOpened
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.SideEffect
import com.girrafeec.avito_deezer.ui.screen.player.PlayerViewModel.SideEffect.LaunchBackgroundPlayback
import kotlinx.coroutines.flow.Flow

@Composable
fun PlayerScreenBehavior(
    sideEffects: Flow<SideEffect>,
    onEvent: (Event) -> Unit,
    onHidePlayerClicked: () -> Unit,
    onPlaybackStarted: () -> Unit,
) {
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> onEvent(ScreenOpened)
            else -> Unit
        }
    }

    LaunchedEffect(sideEffects, onPlaybackStarted, onHidePlayerClicked) {
        sideEffects.collect { sideEffect ->
            when (sideEffect) {
                LaunchBackgroundPlayback -> onPlaybackStarted()
            }
        }
    }
}
