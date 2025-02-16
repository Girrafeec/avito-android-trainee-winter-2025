package com.girrafeec.avito_deezer.ui.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme

object AvitoDeezerBottomBarComponents {
    @Composable
    fun BottomBarContainer(
        startContent: @Composable () -> Unit,
        endContent: @Composable () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier.background(color = UiKitTheme.colors.background.primary),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                content = { startContent() },
                modifier = Modifier.weight(1.0f),
            )
            Box(
                contentAlignment = Alignment.Center,
                content = { endContent() },
                modifier = Modifier.weight(1.0f),
            )
        }
    }

    @Composable
    fun NavigationButton(
        item: BottomNavItem,
        isSelected: Boolean,
        onClick: (BottomNavItem) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val buttonContentColor = if (isSelected) {
            UiKitTheme.colors.icon.accent
        } else {
            UiKitTheme.colors.icon.unselected
        }

        val buttonContainerColor = if (isSelected) {
            UiKitTheme.colors.background.secondary
        } else {
            UiKitTheme.colors.background.primary
        }

        Button(
            onClick = { onClick(item) },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = buttonContentColor
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .height(67.dp)
                .padding(4.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(item.iconRedsId),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
