package com.rojer_ko.myalbum.presentation.albums.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.data.room.SavedAlbum
import com.rojer_ko.myalbum.data.room.SavedPhoto
import com.rojer_ko.myalbum.domain.contracts.AlbumsDBRepository
import com.rojer_ko.myalbum.domain.contracts.AlbumsRepository
import com.rojer_ko.myalbum.utils.DataResult
import com.rojer_ko.myalbum.utils.Errors
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val remoteRepository: AlbumsRepository,
    private val dbRepository: AlbumsDBRepository
) : ViewModel() {

    private val _photosResult: MutableLiveData<DataResult<List<PhotoDTO>>> = MutableLiveData()
    val photosResult: LiveData<DataResult<List<PhotoDTO>>> = _photosResult

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("AlbumViewModel", exception.message.toString())
        _photosResult.postValue(DataResult.Error(Errors.UNKNOWN))
    }

    fun getPhotos(albumId: Int) {
        _photosResult.value = DataResult.Process
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _photosResult.postValue(remoteRepository.getPhotos(albumId))
        }
    }

    fun insertAlbum(album: SavedAlbum) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            dbRepository.insertAlbum(album)
        }
    }

    fun insertPhotos(photos: List<SavedPhoto>) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            dbRepository.insertPhotos(photos)
        }
    }
}