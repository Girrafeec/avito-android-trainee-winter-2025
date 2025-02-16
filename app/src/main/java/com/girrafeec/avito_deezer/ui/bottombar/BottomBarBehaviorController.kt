package com.girrafeec.avito_deezer.ui.bottombar

import androidx.compose.runtime.staticCompositionLocalOf
import com.forasoft.androidutils.ui.compose.behavior.BehaviorController
import com.forasoft.androidutils.ui.compose.behavior.NoopBehaviorController

typealias BottomBarBehaviorController = BehaviorController<BottomBarBehavior>

val LocalBottomBarBehaviorController =
    staticCompositionLocalOf<BottomBarBehaviorController> {
        val defaultBehavior = BottomBarBehavior.Visible
        NoopBehaviorController(defaultBehavior)
    }
