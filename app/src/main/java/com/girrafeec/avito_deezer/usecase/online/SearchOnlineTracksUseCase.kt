package com.girrafeec.avito_deezer.usecase.online

import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.usecase.base.UseCase
import com.girrafeec.avito_deezer.usecase.online.SearchOnlineTracksUseCase.Params
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchOnlineTracksUseCase @Inject constructor(
    private val repository: TracksRepository,
) : UseCase<Params, List<Track>>(Dispatchers.IO) {

    override suspend fun execute(params: Params): List<Track> {
        return repository.searchOnlineTracks(searchQuery = params.searchQuery)
    }

    data class Params(val searchQuery: String)
}
