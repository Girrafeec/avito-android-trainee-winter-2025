package com.girrafeec.avito_deezer.domain

import kotlin.time.Duration

data class Track(
    val id: Long,
    val title: String,
    val duration: Duration,
    val artist: Artist,
    val album: Album,
    val trackUri: String? = null,
    val trackUrl: String? = null,
)
