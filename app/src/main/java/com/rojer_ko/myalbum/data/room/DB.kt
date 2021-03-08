package com.rojer_ko.myalbum.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedAlbum::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
}