package com.rojer_ko.myalbum.presentation.albums.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.domain.contracts.AlbumsRepository
import com.rojer_ko.myalbum.utils.DataResult
import com.rojer_ko.myalbum.utils.Errors
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(private val repository: AlbumsRepository) : ViewModel() {

    private val _photosResult: MutableLiveData<DataResult<List<PhotoDTO>>> = MutableLiveData()
    val photosResult: LiveData<DataResult<List<PhotoDTO>>> = _photosResult

    private val photosExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("AlbumViewModel", exception.message.toString())
        _photosResult.postValue(DataResult.Error(Errors.UNKNOWN))
    }

    fun getPhotos(albumId: Int) {
        _photosResult.value = DataResult.Process
        viewModelScope.launch(Dispatchers.IO + photosExceptionHandler) {
            _photosResult.postValue(repository.getPhotos(albumId))
        }
    }
}