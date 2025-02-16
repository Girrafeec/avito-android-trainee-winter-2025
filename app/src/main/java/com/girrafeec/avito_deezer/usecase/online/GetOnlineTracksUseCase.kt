package com.girrafeec.avito_deezer.usecase.online

import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.usecase.base.ParameterlessUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetOnlineTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) : ParameterlessUseCase<List<Track>>(Dispatchers.IO) {

    override suspend fun execute(params: Unit): List<Track> {
        return repository.getOnlineTracks()
    }
}
