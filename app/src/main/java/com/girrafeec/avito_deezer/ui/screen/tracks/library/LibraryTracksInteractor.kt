package com.girrafeec.avito_deezer.ui.screen.tracks.library

import com.girrafeec.avito_deezer.usecase.library.GetLibraryTracksUseCase
import javax.inject.Inject

class LibraryTracksInteractor @Inject constructor(
    val getLibraryTracks: GetLibraryTracksUseCase
)
