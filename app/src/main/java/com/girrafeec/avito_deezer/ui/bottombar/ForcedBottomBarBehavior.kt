package com.girrafeec.avito_deezer.ui.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.girrafeec.avito_deezer.ui.bottombar.BottomBarBehavior.Hidden
import com.girrafeec.avito_deezer.ui.bottombar.BottomBarBehavior.Visible

@Composable
fun ForcedBottomBarBehavior(isVisible: Boolean) {
    val controller = LocalBottomBarBehaviorController.current
    DisposableEffect(isVisible, controller) {
        val behavior = if (isVisible) {
            Visible
        } else {
            Hidden
        }
        controller.pushBehavior(behavior)
        onDispose { controller.popBehavior(behavior) }
    }
}
