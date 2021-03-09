package com.rojer_ko.myalbum.data.repository

import com.rojer_ko.myalbum.data.room.AlbumsDao
import com.rojer_ko.myalbum.data.room.SavedAlbum
import com.rojer_ko.myalbum.data.room.SavedPhoto
import com.rojer_ko.myalbum.domain.contracts.AlbumsDBRepository
import kotlinx.coroutines.flow.Flow

class AlbumsDBRepositoryImpl(private val dao: AlbumsDao) : AlbumsDBRepository {
    override val albums: Flow<List<SavedAlbum>> = dao.getSavedAlbums()

    override suspend fun insertAlbum(album: SavedAlbum) {
        dao.insertAlbum(album)
    }

    override suspend fun deleteAlbum(album: SavedAlbum) {
        dao.deleteAlbum(album)
    }

    override suspend fun insertPhotos(photos: List<SavedPhoto>) {
        dao.insertPhotos(photos)
    }

    override fun getSavedPhotos(albumId: Int): Flow<List<SavedPhoto>> {
        return dao.getSavedPhotos(albumId)
    }
}