package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.domain.Track
import javax.inject.Inject

class TracksRepository @Inject constructor(
    private val remoteDataSource: TracksRemoteDataSource,
    private val localDataSource: TracksLocalDataSource,
) {
    suspend fun getOnlineTracks(): List<Track> {
        return remoteDataSource.getTracks()
    }

    suspend fun searchOnlineTracks(searchQuery: String): List<Track> {
        return remoteDataSource.searchTracks(searchQuery)
    }

    suspend fun getLibraryTracks(): List<Track> {
        return localDataSource.getTracks()
    }

    suspend fun searchLibraryTracks(searchQuery: String): List<Track> {
        return TODO()
    }
}
