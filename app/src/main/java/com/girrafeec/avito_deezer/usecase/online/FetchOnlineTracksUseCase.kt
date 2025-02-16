package com.girrafeec.avito_deezer.usecase.online

import com.forasoft.androidutils.clean.usecase.parameterless.UseCase
import com.girrafeec.avito_deezer.data.TracksRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchOnlineTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) : UseCase<Unit>(Dispatchers.IO) {

    override suspend fun execute(params: Unit) {
        repository.fetchOnlineTracks()
    }
}
