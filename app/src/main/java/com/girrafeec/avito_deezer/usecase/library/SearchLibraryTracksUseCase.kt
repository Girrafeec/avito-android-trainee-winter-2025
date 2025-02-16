package com.girrafeec.avito_deezer.usecase.library

import com.forasoft.androidutils.clean.usecase.UseCase
import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.domain.Track
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchLibraryTracksUseCase @Inject constructor(
    private val repository: TracksRepository,
) : UseCase<SearchLibraryTracksUseCase.Params, List<Track>>(Dispatchers.IO) {

    override suspend fun execute(params: Params): List<Track> {
        return repository.searchLibraryTracks(searchQuery = params.searchQuery)
    }

    data class Params(val searchQuery: String)
}