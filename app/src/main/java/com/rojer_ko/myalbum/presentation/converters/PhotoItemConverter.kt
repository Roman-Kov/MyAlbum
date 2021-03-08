package com.rojer_ko.myalbum.presentation.converters

import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.presentation.albums.ui.PhotoContainer

class PhotoItemConverter {
    companion object {

        fun convertToContainer(
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
    }
}