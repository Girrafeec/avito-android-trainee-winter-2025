package com.girrafeec.avito_deezer.ui.screen.tracks.online

import com.girrafeec.avito_deezer.usecase.GetOnlineTracksUseCase
import javax.inject.Inject

// TODO: [Medium priority] Add fetch-get caching?
class OnlineTracksInteractor @Inject constructor(
    val getOnlineTracks: GetOnlineTracksUseCase,
)
