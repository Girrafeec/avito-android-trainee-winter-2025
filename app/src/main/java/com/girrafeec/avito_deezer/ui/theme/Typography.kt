package com.girrafeec.avito_deezer.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.girrafeec.avito_deezer.R

private val RobotoRegular = Font(
    resId = R.font.roboto,
    weight = FontWeight.W400,
)

private val RobotoMedium = Font(
    resId = R.font.roboto_medium,
    weight = FontWeight.W500,
)

private val RobotoBold = Font(
    resId = R.font.roboto_bold,
    weight = FontWeight.W600,
)

private val RobotoFamily = FontFamily(
    RobotoRegular,
    RobotoMedium,
    RobotoBold,
)

@Immutable
data class UiKitTypography(
    val heading: Heading = Heading(),
    val text: Text = Text(),
) {
    @Immutable
    data class Heading(
        val s: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = RobotoFamily,
            lineHeight = 24.sp,
        ),
        val m: TextStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            fontFamily = RobotoFamily,
            lineHeight = 26.sp,
        ),
        val l: TextStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.W600,
            fontFamily = RobotoFamily,
            lineHeight = 28.sp,
        ),
        val xl: TextStyle = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.W600,
            fontFamily = RobotoFamily,
            lineHeight = 34.sp,
        ),
    )

    @Immutable
    data class Text(
        val basic: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = RobotoFamily,
            lineHeight = 24.sp,
        ),
        val m: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            fontFamily = RobotoFamily,
            lineHeight = 20.sp,
        ),
    )
}

val LocalUiKitTypography = staticCompositionLocalOf { UiKitTypography() }
