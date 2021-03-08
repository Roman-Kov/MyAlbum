package com.rojer_ko.myalbum.data.retrofit

import com.google.gson.JsonArray
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("albums")
    suspend fun getAlbumsAsync(): Response<JsonArray>

    @GET("photos")
    suspend fun getPhotosAsync(
        @Query("albumId") albumId: Int
    ): Response<JsonArray>
}