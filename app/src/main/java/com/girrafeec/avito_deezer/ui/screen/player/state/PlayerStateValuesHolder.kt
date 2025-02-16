package com.girrafeec.avito_deezer.ui.screen.player.state

import com.girrafeec.avito_deezer.domain.PlaybackState
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

class PlayerStateValuesHolder(
    val playbackState: StateFlow<PlaybackState>,
    val playbackPositionMillis: StateFlow<Long>,
    val playbackProgress: StateFlow<Float>,
    val duration: StateFlow<Duration>,
)
