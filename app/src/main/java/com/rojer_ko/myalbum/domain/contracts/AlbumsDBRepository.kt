package com.rojer_ko.myalbum.domain.contracts

import com.rojer_ko.myalbum.data.room.SavedAlbum
import kotlinx.coroutines.flow.Flow

interface AlbumsDBRepository {

    val albums: Flow<List<SavedAlbum>>
    suspend fun insertAlbum(album: SavedAlbum)
    suspend fun deleteAlbum(album: SavedAlbum)
}