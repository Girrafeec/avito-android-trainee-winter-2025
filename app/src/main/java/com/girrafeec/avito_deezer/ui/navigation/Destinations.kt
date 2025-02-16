package com.girrafeec.avito_deezer.ui.navigation

import com.forasoft.androidutils.ui.compose.navigation.parameterless.SimpleDestination

object Destinations {
    data object HomeDestination : SimpleDestination() {
        override val routeSchema: String = Route.Home.route
    }

    data object LibraryDestination : SimpleDestination() {
        override val routeSchema: String = Route.Library.route
    }
}
