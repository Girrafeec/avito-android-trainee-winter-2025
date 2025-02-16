package com.girrafeec.avito_deezer.component

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.girrafeec.avito_deezer.domain.Album
import com.girrafeec.avito_deezer.domain.Artist
import com.girrafeec.avito_deezer.domain.Track
import com.girrafeec.avito_deezer.domain.TrackSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class AvitoDeezerContentResolver @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val contentResolver = checkNotNull(context.contentResolver) {
        "ContentResolver is null"
    }

    fun getLocalTracks(): List<Track> {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"
        val cursor = contentResolver.query(
            /* uri = */ MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            /* projection = */ projection,
            /* selection = */ selection,
            /* selectionArgs = */ null,
            /* sortOrder = */ sortOrder,
        )
        return cursor?.use { cursor ->
            extractTracks(cursor)
        } ?: emptyList()
    }

    fun getMusicFileUri(fileName: String): Uri? {
        val projection = arrayOf(MediaStore.Audio.Media._ID)
        val selection = "${MediaStore.Audio.Media.DATA} = ?"
        val selectionArgs = arrayOf(fileName)

        val cursor = contentResolver.query(
            /* uri = */ MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            /* projection = */ projection,
            /* selection = */ selection,
            /* selectionArgs = */ selectionArgs,
            /* sortOrder = */ null,
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
            }
        }
        return null
    }

    // TODO: [High] Add method to read album cover
    private fun extractTracks(cursor: Cursor): List<Track> {
        val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        val dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
        val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        val albumIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

        val tracks = mutableListOf<Track>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val title = cursor.getString(titleColumn)
            val duration = cursor.getLong(durationColumn)
            val data = cursor.getString(dataColumn)
            val artist = cursor.getString(artistColumn)
            val album = cursor.getString(albumColumn)

            val track = Track(
                id = id,
                title = title,
                duration = duration.toDuration(DurationUnit.MILLISECONDS),
                trackUri = data,
                artist = Artist(name = artist),
                album = Album(
                    title = album,
                    coverUri = ""
                ),
                trackSource = TrackSource.LIBRARY,
            )
            tracks.add(track)
        }
        return tracks.toList()
    }

    companion object {
        private const val TAG = "AvitoDeezerContentResolver"
    }
}
