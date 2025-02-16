package com.girrafeec.avito_deezer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.forasoft.androidutils.ui.compose.navigation.Destination
import com.forasoft.androidutils.ui.compose.navigation.composableDestination
import com.girrafeec.avito_deezer.ui.screen.tracks.library.LibraryTracksScreen
import com.girrafeec.avito_deezer.ui.screen.tracks.online.OnlineTracksScreen

@Composable
fun AvitoDeezerNavigation(
    navController: NavHostController,
    startDestination: Destination<*>,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.routeSchema,
        modifier = modifier,
    ) {
        homeScreen(navController)
        libraryScreen(navController)
//        playerScreen(navController)
    }
}

private fun NavGraphBuilder.homeScreen(navController: NavHostController) {
    composableDestination(
        destination = Destinations.HomeDestination,
        content = {
            OnlineTracksScreen()
        }
    )
}

private fun NavGraphBuilder.libraryScreen(navController: NavHostController) {
    composableDestination(
        destination = Destinations.LibraryDestination,
        content = {
            LibraryTracksScreen()
        }
    )
}

//private fun NavGraphBuilder.playerScreen(navController: NavHostController) {
//    TODO()
//}
