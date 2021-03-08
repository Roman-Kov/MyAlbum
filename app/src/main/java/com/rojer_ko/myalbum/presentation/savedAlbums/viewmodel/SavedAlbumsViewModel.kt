package com.rojer_ko.myalbum.presentation.savedAlbums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rojer_ko.myalbum.data.room.SavedAlbum
import com.rojer_ko.myalbum.domain.contracts.AlbumsDBRepository
import kotlinx.coroutines.launch

class SavedAlbumsViewModel(private val repository: AlbumsDBRepository) : ViewModel() {

    val albums: LiveData<List<SavedAlbum>> =
        repository.albums.asLiveData(viewModelScope.coroutineContext, 0)

    fun deleteAlbum(album: SavedAlbum) {
        viewModelScope.launch {
            repository.deleteAlbum(album)
        }
    }
}