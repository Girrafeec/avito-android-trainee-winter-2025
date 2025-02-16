package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.data.network.DeezerApi
import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// TODO: [Medium priority] Add paging
@Singleton
class TracksRemoteDataSource @Inject constructor(
    private val api: DeezerApi,
) : TracksDataSource {

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    override val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    override suspend fun fetchTracks() {
        val tracksResponse = api.getTrackChart().tracks
        val updatedTracks = _tracks.value.toMutableSet().apply {
            addAll(tracksResponse.toTracks())
        }
        _tracks.value = updatedTracks.toList()
    }

    override suspend fun searchTracks(searchQuery: String): List<Track> {
        val tracksResponse = api.searchTracks(searchQuery)
        return tracksResponse.toTracks()
    }
}
