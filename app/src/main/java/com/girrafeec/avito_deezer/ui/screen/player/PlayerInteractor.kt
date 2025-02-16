package com.girrafeec.avito_deezer.ui.screen.player

import com.girrafeec.avito_deezer.component.player.AvitoDeezerPlayer
import com.girrafeec.avito_deezer.data.TracksRepository
import com.girrafeec.avito_deezer.ui.screen.player.state.getPlayerStateValuesHolder
import javax.inject.Inject

class PlayerInteractor @Inject constructor(
    repository: TracksRepository,
    player: AvitoDeezerPlayer,
) {
    val onlineTracksFlow = repository.onlineTracks
    val libraryTracksFlow = repository.libraryTracks
    val playerStateValuesHolder = player.getPlayerStateValuesHolder()
}
