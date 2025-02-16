package com.girrafeec.avito_deezer.ui.bottombar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.girrafeec.avito_deezer.ui.bottombar.AvitoDeezerBottomBarComponents.BottomBarContainer
import com.girrafeec.avito_deezer.ui.bottombar.AvitoDeezerBottomBarComponents.NavigationButton

@Composable
fun AvitoDeezerBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val bottomBarBehaviorController = LocalBottomBarBehaviorController.current
    val bottomBarBehavior by bottomBarBehaviorController.currentBehavior.collectAsStateWithLifecycle()
    val isBottomBarVisible = bottomBarBehavior is BottomBarBehavior.Visible

    if (isBottomBarVisible) {
        AvitoDeezerBottomBarContent(
            navController = navController,
            modifier = modifier,
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
private fun AvitoDeezerBottomBarContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val backStack =
        navController.currentBackStack.collectAsStateWithLifecycle()

    BottomBarContainer(
        startContent = {
            NavigationButton(
                item = BottomNavItem.Home,
                isSelected = isBottomNavItemSelected(
                    BottomNavItem.Home,
                    backStack.value
                ),
                onClick = { navController.navigateToBottomNavItem(it) }
            )
        },
        endContent = {
            NavigationButton(
                item = BottomNavItem.Library,
                isSelected = isBottomNavItemSelected(
                    BottomNavItem.Library,
                    backStack.value
                ),
                onClick = { navController.navigateToBottomNavItem(it) }
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}
