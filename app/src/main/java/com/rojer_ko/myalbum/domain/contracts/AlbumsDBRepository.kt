package com.rojer_ko.myalbum.domain.contracts

import com.rojer_ko.myalbum.data.room.SavedAlbum
import com.rojer_ko.myalbum.data.room.SavedPhoto
import kotlinx.coroutines.flow.Flow

interface AlbumsDBRepository {

    val albums: Flow<List<SavedAlbum>>
    suspend fun insertAlbum(album: SavedAlbum)
    suspend fun deleteAlbum(album: SavedAlbum)
    suspend fun insertPhotos(photos: List<SavedPhoto>)
    fun getSavedPhotos(albumId: Int): Flow<List<SavedPhoto>>
}