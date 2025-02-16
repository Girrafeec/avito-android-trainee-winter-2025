package com.girrafeec.avito_deezer.ui.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.girrafeec.avito_deezer.R
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme
import com.girrafeec.avito_deezer.util.optional

object PlayerScreenComponents {
    @Composable
    fun TopBar(
        track: Track,
        onHidePlayerClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            HidePlayerButton(
                onClick = onHidePlayerClick,
            )
            NowPlayingTitle(
                track = track,
            )
        }
    }

    @Composable
    fun TrackCard() {

    }

    @Composable
    fun PlaybackProgressSlider() {

    }

    @Composable
    fun PlaybackControls(
        isPlaying: Boolean,
        onPlaybackToggled: () -> Unit,
        onJumpToPrevTrackClicked: () -> Unit,
        onJumpToNextTrackClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            JumpToTrackButton(
                onClick = onJumpToPrevTrackClicked,
                isReversed = true,
                modifier = Modifier.padding(end = 24.dp)
            )
            PlaybackButton(
                isPlaying = isPlaying,
                onClick = onPlaybackToggled,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            JumpToTrackButton(
                onClick = onJumpToNextTrackClicked,
                modifier = Modifier.padding(start = 24.dp)
            )
        }
    }

    @Composable
    private fun HidePlayerButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(
                    color = UiKitTheme.colors.background.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .size(32.dp)
                .clickable(onClick = onClick)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_down),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    @Composable
    private fun NowPlayingTitle(
        track: Track,
        modifier: Modifier = Modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier,
        ) {
            Text(
                text = stringResource(R.string.screen_player_now_playing)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = track.album.title
            )
        }
    }

    @Composable
    private fun PlaybackButton(
        isPlaying: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(
                    color = UiKitTheme.colors.background.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .size(64.dp)
                .clickable(onClick = onClick)
        ) {
            val iconResId = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
            )
        }
    }

    @Composable
    private fun JumpToTrackButton(
        onClick: () -> Unit,
        isReversed: Boolean = false,
        modifier: Modifier = Modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(
                    color = UiKitTheme.colors.background.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .size(40.dp)
                .clickable(onClick = onClick)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .optional(isReversed) {
                        rotate(180f)
                    }
            )
        }
    }
}
