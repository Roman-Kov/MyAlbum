package com.rojer_ko.myalbum.data.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("albums")
    suspend fun getAlbumsAsync(): Response<JsonObject>
}