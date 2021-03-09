package com.rojer_ko.myalbum.data.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_photos",
    foreignKeys = [ForeignKey(
        entity = SavedAlbum::class,
        parentColumns = ["id"],
        childColumns = ["albumId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class SavedPhoto(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
