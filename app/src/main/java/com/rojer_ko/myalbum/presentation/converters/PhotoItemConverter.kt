package com.rojer_ko.myalbum.presentation.converters

import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.data.room.SavedPhoto
import com.rojer_ko.myalbum.presentation.albums.ui.PhotoContainer
import com.rojer_ko.myalbum.presentation.savedAlbums.ui.SavedPhotoContainer

class PhotoItemConverter {
    companion object {

        fun convertPhotoDTOToContainer(
            photos: List<PhotoDTO>,
            photoOnClick: PhotoContainer.ItemClick
        ): List<PhotoContainer> {
            val photoContainers = mutableListOf<PhotoContainer>()

            for (photo in photos) {
                photoContainers.add(
                    PhotoContainer(
                        photo.title,
                        photo.url,
                        photo.thumbnailUrl,
                        photoOnClick
                    )
                )
            }
            return photoContainers
        }

        fun convertSavedPhotoToContainer(
            photos: List<SavedPhoto>
        ): List<SavedPhotoContainer> {
            val photoContainers = mutableListOf<SavedPhotoContainer>()

            for (photo in photos) {
                photoContainers.add(
                    SavedPhotoContainer(
                        photo.title,
                        photo.thumbnailUrl
                    )
                )
            }
            return photoContainers
        }
    }
}