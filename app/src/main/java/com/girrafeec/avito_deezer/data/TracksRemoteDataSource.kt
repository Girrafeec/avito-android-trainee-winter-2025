package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.data.network.DeezerApi
import com.girrafeec.avito_deezer.domain.Track
import javax.inject.Inject

class TracksRemoteDataSource @Inject constructor(
    private val api: DeezerApi,
) : TracksDataSource {

    override suspend fun getTracks(): List<Track> {
        val tracksResponse = api.getTrackChart().tracks
        return tracksResponse.toTracks()
    }

    override suspend fun searchTracks(searchQuery: String): List<Track> {
        val tracksResponse = api.searchTracks(searchQuery)
        return tracksResponse.toTracks()
    }
}
