package com.girrafeec.avito_deezer.data.network

import retrofit2.http.GET

interface DeezerApi {
    @GET("chart")
    suspend fun getTrackChart(): GetTrackChartResponse
}
