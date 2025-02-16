package com.girrafeec.avito_deezer.ui.screen.tracks.library

import com.girrafeec.avito_deezer.usecase.library.FetchLibraryTracksUseCase
import com.girrafeec.avito_deezer.usecase.library.GetLibraryTracksUseCase
import com.girrafeec.avito_deezer.usecase.library.SearchLibraryTracksUseCase
import javax.inject.Inject

class LibraryTracksInteractor @Inject constructor(
    val getLibraryTracks: GetLibraryTracksUseCase,
    val fetchLibraryTracks: FetchLibraryTracksUseCase,
    val searchLibraryTracks: SearchLibraryTracksUseCase,
)
