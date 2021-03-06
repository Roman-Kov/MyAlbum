package com.rojer_ko.myalbum.domain.contracts

import com.rojer_ko.myalbum.data.model.AlbumDTO
import com.rojer_ko.myalbum.utils.DataResult

interface AlbumsRepository {

    suspend fun getAlbums(): DataResult<List<AlbumDTO>>
}