package com.girrafeec.avito_deezer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.forasoft.androidutils.ui.compose.behavior.BehaviorController
import com.forasoft.androidutils.ui.compose.behavior.DefaultBehaviorController
import com.girrafeec.avito_deezer.ui.bottombar.BottomBarBehavior
import com.girrafeec.avito_deezer.ui.bottombar.LocalBottomBarBehaviorController
import com.girrafeec.avito_deezer.ui.navigation.Destinations
import com.girrafeec.avito_deezer.ui.theme.AvitoDeezerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val bottomBarBehaviorController: BehaviorController<BottomBarBehavior> =
                remember {
                    val defaultBehavior = BottomBarBehavior.Visible
                    DefaultBehaviorController(defaultBehavior)
                }

            val navController = rememberNavController()
            AvitoDeezerTheme {
                CompositionLocalProvider(
                    LocalBottomBarBehaviorController provides bottomBarBehaviorController
                ) {
                    AvitoDeezerApp(
                        navController = navController,
                        startDestination = Destinations.HomeDestination
                    )
                }
            }
        }
    }
}
