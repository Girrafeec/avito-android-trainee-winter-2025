package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.component.AvitoDeezerContentResolver
import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TracksLocalDataSource @Inject constructor(
    private val contentResolver: AvitoDeezerContentResolver,
) : TracksDataSource {

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    override val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    override suspend fun fetchTracks() {
        val localTracks = contentResolver.getLocalTracks()
        val updatedTracks = _tracks.value.toMutableSet().apply {
            addAll(localTracks)
        }
        _tracks.value = updatedTracks.toList()
    }

    override suspend fun searchTracks(searchQuery: String): List<Track> {
        val tracks = _tracks.value
        val query = searchQuery.trim().lowercase()
        return tracks.filter { track ->
            track.title.contains(query, ignoreCase = true) ||
                    track.artist.name.contains(query, ignoreCase = true) ||
                    track.album.title.contains(query, ignoreCase = true)
        }
    }
}
