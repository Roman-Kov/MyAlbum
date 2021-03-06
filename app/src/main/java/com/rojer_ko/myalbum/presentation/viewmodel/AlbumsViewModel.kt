package com.rojer_ko.myalbum.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.myalbum.data.model.AlbumDTO
import com.rojer_ko.myalbum.domain.contracts.AlbumsRepository
import com.rojer_ko.myalbum.utils.DataResult
import com.rojer_ko.myalbum.utils.Errors
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel() {

    private val _albumsResult: MutableLiveData<DataResult<List<AlbumDTO>>> = MutableLiveData()
    val albumsResult: LiveData<DataResult<List<AlbumDTO>>> = _albumsResult

    private val albumsExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("AlbumsViewModel", exception.message.toString())
        _albumsResult.postValue(DataResult.Error(Errors.UNKNOWN))
    }

    fun getAlbums() {
        _albumsResult.value = DataResult.Process
        viewModelScope.launch(Dispatchers.IO + albumsExceptionHandler) {
            _albumsResult.postValue(repository.getAlbums())
        }
    }
}