package com.girrafeec.avito_deezer.domain

enum class PlaybackState {
    NOT_PLAYING, LOADING, PLAYING;

    val isPlaying: Boolean
        get() = this == PLAYING

    val isLoading: Boolean
        get() = this == LOADING
}
