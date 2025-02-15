package com.girrafeec.avito_deezer.ui.screen.tracks.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.girrafeec.avito_deezer.R
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.ui.theme.UiKitTheme

object TracksScreenComponents {
    @Composable
    fun TracksSearchField(
        searchQuery: String,
        onSearchInputChanged: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        // TODO: [High] Add start icon
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchInputChanged,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.screen_tracks_search_field_placeholder),
                    style = UiKitTheme.typography.text.basic,
                    color = UiKitTheme.colors.text.secondary
                )
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    tint = UiKitTheme.colors.icon.primary,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(32.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = UiKitTheme.colors.background.secondary,
                unfocusedContainerColor = UiKitTheme.colors.background.secondary,
                focusedTextColor = UiKitTheme.colors.text.primary,
                focusedBorderColor = UiKitTheme.colors.border.primary,
                unfocusedBorderColor = UiKitTheme.colors.border.primary,
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }

    @Composable
    fun Tracks(
        tracks: List<Track>,
        onTrackClicked: (Track) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        LazyColumn {
            items(
                items = tracks,
                key = { track -> track.id }
            ) { track ->
                Track(
                    track = track,
                    onTrackClicked = onTrackClicked,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // TODO: [High] Add click effect
    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    private fun Track(
        track: Track,
        onTrackClicked: (Track) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val model = track.trackUrl ?: track.trackUri
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            GlideImage(
                model = model,
                contentDescription = null,
                loading = placeholder(R.drawable.ic_disk),
                failure = placeholder(R.drawable.ic_disk),
                modifier = Modifier
                    .background(
                        color = UiKitTheme.colors.background.placeholder,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .size(56.dp)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .clickable { onTrackClicked(track) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = track.title,
                    style = UiKitTheme.typography.heading.m,
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
