package com.girrafeec.avito_deezer.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.forasoft.androidutils.kotlin.coroutines.JobHolder
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AvitoDeezerSlider(
    value: Float,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueColor: Color = UiKitTheme.colors.background.placeholder,
    backgroundColor: Color = UiKitTheme.colors.background.secondary,
    thumbColor: Color = UiKitTheme.colors.icon.accent
) {
    val normalizedValue = value.coerceIn(0f, 1f)

    val valueState = animateFloatAsState(
        targetValue = normalizedValue,
        animationSpec = remember {
            tween(durationMillis = ValueAnimationDuration, easing = LinearEasing)
        },
        label = "AvitoDeezerSlider value"
    )

    val isDragged = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(SliderHeight)
            .drawBehind {
                drawBackground(backgroundColor)
                drawValue(
                    progress = valueState.value,
                    color = valueColor
                )

                drawThumb(
                    value = valueState.value,
                    color = thumbColor,
                )
            }
            .wrapContentHeight(unbounded = true)
    ) {
        GestureDetector(
            onIsDraggedChanged = { isDragged.value = it },
            onValueChanged = onValueChanged,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GestureDetector(
    onIsDraggedChanged: (Boolean) -> Unit,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val updatedOnIsDraggedChanged = rememberUpdatedState(onIsDraggedChanged)

    val coroutineScope = rememberCoroutineScope()
    val updateIsDraggedOnPressJob = remember { JobHolder() }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                var dragOffset = 0f
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        updatedOnIsDraggedChanged.value(true)
                        dragOffset = offset.x.coerceAtLeast(0f)
                    },
                    onDragEnd = {
                        updateIsDraggedOnPressJob.job?.cancel()
                        updatedOnIsDraggedChanged.value(false)
                    },
                    onDragCancel = {
                        updateIsDraggedOnPressJob.job?.cancel()
                        updatedOnIsDraggedChanged.value(false)
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset += dragAmount
                        val newValue = (dragOffset / size.width).coerceAtLeast(0f)
                        onValueChanged(newValue)
                    }
                )
            }
            .pointerInput(updateIsDraggedOnPressJob, coroutineScope) {
                detectTapGestures(
                    onPress = { offset ->
                        updateIsDraggedOnPressJob interruptWith coroutineScope.launch {
                            delay(150.milliseconds)
                            updatedOnIsDraggedChanged.value(true)
                        }
                        val newValue = (offset.x / size.width).coerceAtLeast(0f)
                        onValueChanged(newValue)
                    },
                    onTap = {
                        updateIsDraggedOnPressJob.job?.cancel()
                        updatedOnIsDraggedChanged.value(false)
                    }
                )
            }
            .minimumInteractiveComponentSize()
    )
}

private fun DrawScope.drawBackground(color: Color) {
    val cornerRadius = CornerRadius(size.height / 2)
    drawRoundRect(
        color = color,
        cornerRadius = cornerRadius
    )
}

private fun DrawScope.drawValue(progress: Float, color: Color) {
    val valueCornerSize = size.height / 2
    val valueTopLeft = Offset(-valueCornerSize, 0f)
    val valueWidth = size.width * progress.coerceAtLeast(0f) + valueCornerSize * 2
    val valueSize = Size(valueWidth, size.height)
    val valueCornerRadius = CornerRadius(valueCornerSize)
    drawRoundRect(
        color = color,
        topLeft = valueTopLeft,
        size = valueSize,
        cornerRadius = valueCornerRadius,
    )
}

private fun DrawScope.drawThumb(
    value: Float,
    color: Color,
) {
    val thumbSizePx = ThumbSize.toPx()
    val thumbRadius = thumbSizePx / 2
    val thumbCenter = Offset(
        x = size.width * value,
        y = size.height / 2,
    )
    drawCircle(
        color = color,
        radius = thumbRadius,
        center = thumbCenter,
    )
}

private const val ValueAnimationDuration = 50

private val SliderHeight = 6.dp

private val ThumbSize = 16.dp
