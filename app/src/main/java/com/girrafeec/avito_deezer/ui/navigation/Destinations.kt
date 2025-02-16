package com.girrafeec.avito_deezer.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.forasoft.androidutils.ui.compose.navigation.Destination
import com.forasoft.androidutils.ui.compose.navigation.OptionalNavArg
import com.forasoft.androidutils.ui.compose.navigation.RouteUtils
import com.forasoft.androidutils.ui.compose.navigation.parameterless.SimpleDestination
import com.girrafeec.avito_deezer.domain.TrackSource

object Destinations {
    data object HomeDestination : SimpleDestination() {
        override val routeSchema: String = Route.Home.route
    }

    data object LibraryDestination : SimpleDestination() {
        override val routeSchema: String = Route.Library.route
    }

    data object PlayerDestination : Destination<PlayerDestination.Args>() {

        const val KeyTrackId = "trackId"
        const val KeyTrackSource = "trackSource"

        const val TrackIdDefaultValue = -1L

        override val routeSchema: String = RouteUtils.generateRouteSchema(
            routeBase = Route.Player.route,
            optionalArgNames = arrayOf(
                KeyTrackId,
                KeyTrackSource,
            )
        )

        override fun createRoute(args: Args): String {
            return RouteUtils.generateRoute(
                routeBase = Route.Player.route,
                optionalArgs = arrayOf(
                    OptionalNavArg(KeyTrackId, args.trackId),
                    OptionalNavArg(KeyTrackSource, args.trackSource)
                )
            )
        }

        override val arguments: List<NamedNavArgument>
            get() = listOf(
                navArgument(KeyTrackId) {
                    type = NavType.LongType
                },
                navArgument(KeyTrackSource) {
                    type = NavType.EnumType(TrackSource::class.java)
                },
            )

        data class Args(
            val trackId: Long = TrackIdDefaultValue,
            val trackSource: TrackSource,
        )
    }
}
