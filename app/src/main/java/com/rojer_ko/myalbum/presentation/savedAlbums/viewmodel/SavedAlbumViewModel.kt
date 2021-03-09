package com.rojer_ko.myalbum.presentation.savedAlbums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rojer_ko.myalbum.data.room.SavedPhoto
import com.rojer_ko.myalbum.domain.contracts.AlbumsDBRepository

class SavedAlbumViewModel(
    private val dbRepository: AlbumsDBRepository
) : ViewModel() {

    fun getSavedPhotos(albumId: Int): LiveData<List<SavedPhoto>> {
        return dbRepository.getSavedPhotos(albumId).asLiveData(viewModelScope.coroutineContext, 0)
    }
}