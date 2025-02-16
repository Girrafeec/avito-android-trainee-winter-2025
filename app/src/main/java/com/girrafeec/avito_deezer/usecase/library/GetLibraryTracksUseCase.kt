package com.girrafeec.avito_deezer.usecase.library

import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.usecase.base.ParameterlessUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetLibraryTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) : ParameterlessUseCase<List<Track>>(Dispatchers.IO) {
    override suspend fun execute(params: Unit): List<Track> {
        return repository.getLibraryTracks()
    }
}