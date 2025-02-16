package avitodeezericons

import AvitoDeezerIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.Unit

public val AvitoDeezerIcons.IcSearch: ImageVector
    get() {
        if (_icSearch != null) {
            return _icSearch!!
        }
        _icSearch = Builder(name = "IcSearch", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF5E5E5E)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(28.543f, 26.337f)
                lineTo(22.663f, 20.456f)
                curveTo(24.078f, 18.572f, 24.843f, 16.277f, 24.84f, 13.92f)
                curveTo(24.84f, 7.899f, 19.941f, 3.0f, 13.92f, 3.0f)
                curveTo(7.899f, 3.0f, 3.0f, 7.899f, 3.0f, 13.92f)
                curveTo(3.0f, 19.941f, 7.899f, 24.84f, 13.92f, 24.84f)
                curveTo(16.277f, 24.843f, 18.572f, 24.078f, 20.456f, 22.663f)
                lineTo(26.337f, 28.543f)
                curveTo(26.635f, 28.809f, 27.023f, 28.951f, 27.422f, 28.94f)
                curveTo(27.821f, 28.929f, 28.201f, 28.765f, 28.483f, 28.483f)
                curveTo(28.765f, 28.201f, 28.929f, 27.821f, 28.94f, 27.422f)
                curveTo(28.951f, 27.023f, 28.809f, 26.635f, 28.543f, 26.337f)
                close()
                moveTo(6.12f, 13.92f)
                curveTo(6.12f, 12.377f, 6.577f, 10.869f, 7.435f, 9.587f)
                curveTo(8.292f, 8.304f, 9.51f, 7.304f, 10.935f, 6.714f)
                curveTo(12.36f, 6.123f, 13.929f, 5.969f, 15.442f, 6.27f)
                curveTo(16.955f, 6.571f, 18.345f, 7.314f, 19.435f, 8.405f)
                curveTo(20.526f, 9.495f, 21.269f, 10.885f, 21.57f, 12.398f)
                curveTo(21.871f, 13.911f, 21.717f, 15.48f, 21.126f, 16.905f)
                curveTo(20.536f, 18.33f, 19.536f, 19.548f, 18.253f, 20.406f)
                curveTo(16.971f, 21.263f, 15.463f, 21.72f, 13.92f, 21.72f)
                curveTo(11.852f, 21.718f, 9.87f, 20.895f, 8.407f, 19.433f)
                curveTo(6.945f, 17.97f, 6.122f, 15.988f, 6.12f, 13.92f)
                close()
            }
        }
        .build()
        return _icSearch!!
    }

private var _icSearch: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = AvitoDeezerIcons.IcSearch, contentDescription = "")
    }
}
