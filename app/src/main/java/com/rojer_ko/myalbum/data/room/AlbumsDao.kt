package com.rojer_ko.myalbum.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: SavedAlbum)

    @Query("SELECT * from saved_albums ORDER BY id")
    fun getItemList(): Flow<List<SavedAlbum>>

    @Delete
    suspend fun delete(album: SavedAlbum)
}