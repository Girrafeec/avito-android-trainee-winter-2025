package com.girrafeec.avito_deezer.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object Colors {
    val GrayMain = Color(0xFF5E5E5E)
    val Gray800 = GrayMain.copy(alpha = 0.8f)
    val Gray600 = GrayMain.copy(alpha = 0.6f)
    val Gray400 = GrayMain.copy(alpha = 0.4f)
    val Gray200 = GrayMain.copy(alpha = 0.2f)
    val Gray100 = GrayMain.copy(alpha = 0.1f)
    val Gray50 = GrayMain.copy(alpha = 0.05f)

    val WhiteMain = Color(0xFFFFFFFF)
}

val LightColorScheme = lightColorScheme(
    primary = Colors.WhiteMain,
    onPrimary = Colors.GrayMain,
    tertiary = Colors.Gray600
)

@Immutable
data class UiKitColors(
    val background: Background = Background(),
    val text: Text = Text(),
    val border: Border = Border(),
    val icon: Icon = Icon(),
) {

    @Immutable
    data class Background(
        val primary: Color = Colors.WhiteMain,
        val secondary: Color = Colors.Gray50,
        val placeholder: Color = Colors.Gray200,
    )

    @Immutable
    data class Text(
        val primary: Color = Colors.GrayMain,
        val secondary: Color = Colors.Gray800,
    )

    @Immutable
    data class Border(
        val primary: Color = Colors.Gray100,
    )

    @Immutable
    data class Icon(
        val primary: Color = Colors.GrayMain,
        val placeholder: Color = Colors.Gray400,
    )
}

val LocalUiKitColors = staticCompositionLocalOf { UiKitColors() }
