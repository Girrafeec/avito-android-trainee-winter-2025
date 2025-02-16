package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.component.AvitoDeezerContentResolver
import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TracksLocalDataSource @Inject constructor(
    private val contentResolver: AvitoDeezerContentResolver,
) : TracksDataSource {

    // TODO: [High] Use set to return previous values from set and to update it if necessary?
    private val _libraryTracks = MutableStateFlow<Set<Track>>(emptySet())

    override suspend fun getTracks(): List<Track> {
        return contentResolver.getLocalTracks()
    }

    override suspend fun searchTracks(searchQuery: String): List<Track> {
        TODO("Not yet implemented")
    }
}
