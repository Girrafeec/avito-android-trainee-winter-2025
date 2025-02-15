package com.girrafeec.avito_deezer.usecase

import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetOnlineTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) : UseCase<Unit, List<Track>>(Dispatchers.IO) {

    override suspend fun execute(params: Unit): List<Track> {
        return repository.getOnlineTracks()
    }
}
