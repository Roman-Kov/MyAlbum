package com.rojer_ko.myalbum.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_albums")
data class SavedAlbum(
    @PrimaryKey
    val id: Int,
    val title: String
)