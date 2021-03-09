package com.rojer_ko.myalbum.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: SavedAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<SavedPhoto>)

    @Query("SELECT * from saved_albums ORDER BY id")
    fun getSavedAlbums(): Flow<List<SavedAlbum>>

    @Query("SELECT * from saved_photos WHERE id = :albumId ORDER BY id")
    fun getSavedPhotos(albumId: Int): Flow<List<SavedPhoto>>

    @Delete
    suspend fun deleteAlbum(album: SavedAlbum)
}