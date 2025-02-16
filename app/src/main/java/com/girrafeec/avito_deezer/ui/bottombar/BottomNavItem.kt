package com.girrafeec.avito_deezer.ui.bottombar

import androidx.annotation.DrawableRes
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.girrafeec.avito_deezer.R
import com.girrafeec.avito_deezer.ui.navigation.Route

sealed class BottomNavItem(
    val route: String,

    @DrawableRes
    val iconRedsId: Int,
) {
    data object Home : BottomNavItem(
        route = Route.Home.route,
        iconRedsId = R.drawable.ic_home,
    )

    data object Library : BottomNavItem(
        route = Route.Library.route,
        iconRedsId = R.drawable.ic_music_library,
    )
}

// TODO: [High] Process player
fun NavHostController.navigateToBottomNavItem(item: BottomNavItem) {
    this.navigate(item.route) {
//        popUpTo(PlayerDestination.Player.routeSchema)
        launchSingleTop = true
        restoreState = true
    }
}

fun isBottomNavItemSelected(
    bottomNavItem: BottomNavItem,
    backStack: List<NavBackStackEntry>,
): Boolean {
    val lastBottomNavItemEntry = backStack.lastOrNull {
        val route = it.destination.route
        route == BottomNavItem.Home.route || route == BottomNavItem.Library.route
    }
    return lastBottomNavItemEntry?.destination?.route == bottomNavItem.route
}
