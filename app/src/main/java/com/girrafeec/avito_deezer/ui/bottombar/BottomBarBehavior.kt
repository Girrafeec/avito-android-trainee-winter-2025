package com.girrafeec.avito_deezer.ui.bottombar

import com.forasoft.androidutils.ui.compose.behavior.Behavior

sealed interface BottomBarBehavior : Behavior {
    data object Visible : BottomBarBehavior
    data object Hidden : BottomBarBehavior
}
