package com.girrafeec.avito_deezer.data

import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TracksRepository @Inject constructor(
    private val remoteDataSource: TracksRemoteDataSource,
    private val localDataSource: TracksLocalDataSource,
) {
    val onlineTracks: Flow<List<Track>> = remoteDataSource.tracks
    val libraryTracks: Flow<List<Track>> = localDataSource.tracks

    suspend fun fetchOnlineTracks() {
        remoteDataSource.fetchTracks()
    }

    suspend fun searchOnlineTracks(searchQuery: String): List<Track> {
        return remoteDataSource.searchTracks(searchQuery)
    }

    suspend fun fetchLibraryTracks() {
        localDataSource.fetchTracks()
    }

    suspend fun searchLibraryTracks(searchQuery: String): List<Track> {
        return localDataSource.searchTracks(searchQuery)
    }
}
