package com.girrafeec.avito_deezer.ui.screen.tracks.online

import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.usecase.online.FetchOnlineTracksUseCase
import com.girrafeec.avito_deezer.usecase.online.GetOnlineTracksUseCase
import com.girrafeec.avito_deezer.usecase.online.SearchOnlineTracksUseCase
import javax.inject.Inject

// TODO: [Medium priority] Add fetch-get caching?
class OnlineTracksInteractor @Inject constructor(
    val getOnlineTracks: GetOnlineTracksUseCase,
    val fetchOnlineTracks: FetchOnlineTracksUseCase,
    val searchOnlineTracks: SearchOnlineTracksUseCase,
)
