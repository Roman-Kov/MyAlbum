package com.rojer_ko.myalbum.presentation.converters

import com.rojer_ko.myalbum.data.model.AlbumDTO
import com.rojer_ko.myalbum.presentation.albums.ui.AlbumContainer

class AlbumItemConverter {
    companion object {

        fun convertToContainer(
            albums: List<AlbumDTO>,
            albumOnClick: AlbumContainer.ItemClick
        ): List<AlbumContainer> {
            val albumContainers = mutableListOf<AlbumContainer>()

            for (album in albums) {
                albumContainers.add(
                    AlbumContainer(
                        album.title,
                        album.id,
                        albumOnClick
                    )
                )
            }
            return albumContainers
        }
    }
}