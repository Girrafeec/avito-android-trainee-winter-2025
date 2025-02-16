package com.girrafeec.avito_deezer.ui.screen.player

import com.girrafeec.avito_deezer.data.TracksRepository
import javax.inject.Inject

class PlayerInteractor @Inject constructor(
    private val repository: TracksRepository,
) {
    val onlineTracksFlow = repository.onlineTracks
    val libraryTracksFlow = repository.libraryTracks
}
