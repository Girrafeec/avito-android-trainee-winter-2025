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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.girrafeec.avito_deezer.R
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.component.AvitoDeezerSlider
import com.girrafeec.avito_deezer.ui.screen.player.state.PlayerState
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme
import com.girrafeec.avito_deezer.util.DateTime
import com.girrafeec.avito_deezer.util.optional
import kotlin.time.Duration.Companion.milliseconds

object PlayerScreenComponents {
    @Composable
    fun TopBar(
        track: Track,
        onHidePlayerClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            HidePlayerButton(
                onClick = onHidePlayerClick,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            NowPlayingTitle(
                track = track,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun TrackCard(
        track: Track,
        modifier: Modifier = Modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            val model = track.album.coverUrl ?: track.album.coverUri
            GlideImage(
                model = model,
                contentDescription = null,
                loading = placeholder(R.drawable.ic_disk),
                failure = placeholder(R.drawable.ic_disk),
                modifier = Modifier
                    .background(
                        color = UiKitTheme.colors.background.placeholder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(202.dp)
            )
            Spacer(modifier = Modifier.height(56.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    tint = UiKitTheme.colors.icon.placeholder,
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = UiKitTheme.colors.background.placeholder,
                            shape = CircleShape,
                        )
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = track.title,
                        style = UiKitTheme.typography.heading.l,
                        color = UiKitTheme.colors.text.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = track.artist.name,
                        style = UiKitTheme.typography.text.basic,
                        color = UiKitTheme.colors.text.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }

    @Composable
    fun PlaybackProgressSlider(
        playerState: PlayerState,
        onSeek: (Float) -> Unit,
    ) {
        AvitoDeezerSlider(
            value = playerState.playbackProgress,
            onValueChanged = onSeek,
        )
    }

    @Composable
    fun PositionAndDuration(
        playerState: PlayerState,
        modifier: Modifier = Modifier
    ) {
        val positionText by remember(playerState) {
            derivedStateOf {
                DateTime.durationToString(playerState.playbackPositionMillis.milliseconds)
            }
        }

        val durationText by remember(playerState) {
            derivedStateOf { DateTime.durationToString(playerState.duration) }
        }
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = positionText,
                style = UiKitTheme.typography.text.basic,
                color = UiKitTheme.colors.text.primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = durationText,
                style = UiKitTheme.typography.text.basic,
                color = UiKitTheme.colors.text.primary,
            )
        }
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
                tint = UiKitTheme.colors.icon.primary,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            Text(
                text = stringResource(R.string.screen_player_now_playing),
                style = UiKitTheme.typography.text.m,
                color = UiKitTheme.colors.text.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = track.album.title,
                style = UiKitTheme.typography.heading.s,
                color = UiKitTheme.colors.text.primary
            )
        }
    }

    @Composable
    private fun PlaybackButton(
        isPlaying: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val iconResId = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val iconColor = if (isPlaying) {
            UiKitTheme.colors.icon.unselected
        } else {
            UiKitTheme.colors.icon.accent
        }
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
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = iconColor,
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
                tint = UiKitTheme.colors.icon.primary,
                modifier = Modifier
                    .size(32.dp)
                    .optional(isReversed) {
                        rotate(180f)
                    }
            )
        }
    }
}
