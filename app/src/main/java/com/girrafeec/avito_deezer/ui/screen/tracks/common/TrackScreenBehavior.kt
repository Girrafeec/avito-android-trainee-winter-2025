package com.girrafeec.avito_deezer.ui.screen.tracks.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import com.forasoft.androidutils.ui.compose.effect.LifecycleEventObserver
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.bottombar.ForcedBottomBarBehavior
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.Event.ScreenOpened
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect
import com.girrafeec.avito_deezer.ui.screen.tracks.common.BaseTracksViewModel.SideEffect.ShowPlayer
import kotlinx.coroutines.flow.Flow

@Composable
fun TrackScreenBehavior(
    sideEffects: Flow<SideEffect>,
    onEvent: (Event) -> Unit,
    onShowPlayer: (Track) -> Unit,
) {
    ForcedBottomBarBehavior(isVisible = true)

    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> onEvent(ScreenOpened)
            else -> Unit
        }
    }

    LaunchedEffect(sideEffects, onShowPlayer) {
        sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is ShowPlayer -> onShowPlayer(sideEffect.track)
            }
        }
    }
}
