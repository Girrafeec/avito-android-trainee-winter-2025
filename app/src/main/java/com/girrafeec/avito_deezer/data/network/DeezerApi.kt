package com.girrafeec.avito_deezer.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApi {
    @GET("chart")
    suspend fun getTrackChart(): GetTrackChartResponse

    @GET("/search")
    suspend fun searchTracks(@Query("q") searchQuery: String): TracksData
}
