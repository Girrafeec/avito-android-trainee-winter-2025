package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.domain.Track

interface TracksDataSource {
    suspend fun getTracks(): List<Track>

    suspend fun searchTracks(searchQuery: String): List<Track>
}
