package com.girrafeec.avito_deezer.component.player

import com.girrafeec.avito_deezer.domain.TrackSource

data class MediaSource(
    val uri: String,
    val source: TrackSource,
)
