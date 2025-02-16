package com.girrafeec.avito_deezer.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.forasoft.androidutils.ui.compose.navigation.Destination
import com.forasoft.androidutils.ui.compose.navigation.composableDestination
import com.girrafeec.avito_deezer.ui.screen.player.PlayerScreen
import com.girrafeec.avito_deezer.ui.screen.tracks.library.LibraryTracksScreen
import com.girrafeec.avito_deezer.ui.screen.tracks.online.OnlineTracksScreen

@Composable
fun AvitoDeezerNavigation(
    navController: NavHostController,
    startDestination: Destination<*>,
    onPlaybackStarted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.routeSchema,
        modifier = modifier,
    ) {
        homeScreen(navController)
        libraryScreen(navController)
        playerScreen(navController, onPlaybackStarted)
    }
}

private fun NavGraphBuilder.homeScreen(navController: NavHostController) {
    composableDestination(
        destination = Destinations.HomeDestination,
        content = {
            OnlineTracksScreen(
                onShowPlayer = { track ->
                    val args = Destinations.PlayerDestination.Args(
                        trackId = track.id,
                        trackSource = track.trackSource,
                    )
                    val route = Destinations.PlayerDestination.createRoute(args)
                    navController.navigate(route)
                }
            )
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

private fun NavGraphBuilder.playerScreen(
    navController: NavHostController,
    onPlaybackStarted: () -> Unit
) {
    composableDestination(
        destination = Destinations.PlayerDestination,
        content = {
            PlayerScreen(
                onHidePlayerClicked = {
                    navController.popBackStack()
                },
                onPlaybackStarted = onPlaybackStarted
            )
        },
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 300)
            )
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 300)
            )
        }
    )
}
