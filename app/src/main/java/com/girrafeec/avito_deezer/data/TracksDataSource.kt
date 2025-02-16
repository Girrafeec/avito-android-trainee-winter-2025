package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.flow.Flow

interface TracksDataSource {
    val tracks: Flow<List<Track>>

    suspend fun fetchTracks()

    suspend fun searchTracks(searchQuery: String): List<Track>
}
