package com.rojer_ko.myalbum.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.rojer_ko.myalbum.data.model.AlbumDTO
import com.rojer_ko.myalbum.data.model.AlbumsDTO
import com.rojer_ko.myalbum.data.network.NetworkManager
import com.rojer_ko.myalbum.data.retrofit.ApiService
import com.rojer_ko.myalbum.domain.contracts.AlbumsRepository
import com.rojer_ko.myalbum.utils.DataResult
import com.rojer_ko.myalbum.utils.Errors

class AlbumsRepositoryImpl(
    private val api: ApiService,
    private val networkManager: NetworkManager
) : AlbumsRepository {

    private val gson = GsonBuilder().setLenient().create()

    override suspend fun getAlbums(): DataResult<List<AlbumDTO>> {
        if (!networkManager.isNetworkAvailable()) {
            return DataResult.Error(Errors.NETWORK_UNAVAILABLE)
        }
        val response = api.getAlbumsAsync()
        return if (response.isSuccessful) {
            try {
                val albums: List<AlbumDTO> =
                    gson.fromJson(response.body(), AlbumsDTO::class.java).list
                DataResult.Success(albums)
            } catch (e: JsonSyntaxException) {
                Log.e("Api", Exception(e).message.toString())
                DataResult.Error(Errors.BAD_RESPONSE)
            }
        } else {
            DataResult.Error(Errors.BAD_RESPONSE)
        }
    }
}