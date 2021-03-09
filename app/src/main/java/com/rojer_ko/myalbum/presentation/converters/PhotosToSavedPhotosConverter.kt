package com.rojer_ko.myalbum.presentation.converters

import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.data.room.SavedPhoto

class PhotosToSavedPhotosConverter {
    companion object {
        fun convert(photos: List<PhotoDTO>): List<SavedPhoto> {
            val savedPhotos = mutableListOf<SavedPhoto>()
            for (item in photos) {
                val savedPhoto = SavedPhoto(
                    item.albumId,
                    item.id,
                    item.title,
                    item.url,
                    item.thumbnailUrl
                )
                savedPhotos.add(savedPhoto)
            }
            return savedPhotos
        }
    }
}