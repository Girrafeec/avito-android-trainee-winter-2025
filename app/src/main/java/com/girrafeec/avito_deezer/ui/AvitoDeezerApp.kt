package com.girrafeec.avito_deezer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.forasoft.androidutils.ui.compose.navigation.Destination
import com.girrafeec.avito_deezer.ui.bottombar.AvitoDeezerBottomBar
import com.girrafeec.avito_deezer.ui.navigation.AvitoDeezerNavigation
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme

@Composable
fun AvitoDeezerApp(
    navController: NavHostController,
    startDestination: Destination<*>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .background(color = UiKitTheme.colors.background.primary),
    ) {
        AvitoDeezerNavigation(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.weight(1f)
        )

        AvitoDeezerBottomBar(navController = navController)
    }
}
