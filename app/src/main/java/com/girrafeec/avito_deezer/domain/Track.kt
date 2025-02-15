package com.girrafeec.avito_deezer.domain

data class Track(
    val id: Long,
    val title: String,
    val duration: Int,
    val artist: Artist,
    val album: Album,
    val trackUri: String? = null,
    val trackUrl: String? = null,
)
