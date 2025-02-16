package com.girrafeec.avito_deezer.usecase.library

import com.forasoft.androidutils.clean.usecase.parameterless.FlowUseCase
import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLibraryTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) : FlowUseCase<List<Track>>(Dispatchers.IO) {
    override fun execute(params: Unit): Flow<List<Track>> {
        return repository.libraryTracks
    }
}
