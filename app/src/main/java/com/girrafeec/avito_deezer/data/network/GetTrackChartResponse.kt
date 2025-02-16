package com.girrafeec.avito_deezer.data.network

import com.girrafeec.avito_deezer.domain.Album
import com.girrafeec.avito_deezer.domain.Artist
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.domain.TrackSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Serializable
data class GetTrackChartResponse(
    @SerialName("tracks")
    val tracks: TracksData
)

@Serializable
data class TracksData(
    @SerialName("data")
    val tracks: List<TrackDto>
) {
    fun toTracks(): List<Track> {
        return tracks.map {
            Track(
                id = it.id,
                title = it.title,
                duration = it.duration.toDuration(DurationUnit.SECONDS),
                trackUrl = it.previewUrl,
                artist = it.artist.toArtist(),
                album = it.album.toAlbum(),
                trackSource = TrackSource.ONLINE,
            )
        }
    }
}

@Serializable
data class TrackDto(
    @SerialName("id")
    val id: Long,

    @SerialName("title")
    val title: String,

    @SerialName("duration")
    val duration: Long,

    @SerialName("preview")
    val previewUrl: String,

    @SerialName("artist")
    val artist: ArtistDto,

    @SerialName("album")
    val album: AlbumDto,
)

@Serializable
data class ArtistDto(
    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val pictureUrl: String? = null,
) {
    fun toArtist(): Artist {
        return Artist(
            name = name,
            pictureUrl = pictureUrl,
        )
    }
}

@Serializable
data class AlbumDto(
    @SerialName("id")
    val id: Long,

    @SerialName("title")
    val title: String,

    @SerialName("cover")
    val coverUrl: String? = null,
) {
    fun toAlbum(): Album {
        return Album(
            title = title,
            coverUrl = coverUrl,
        )
    }
}
