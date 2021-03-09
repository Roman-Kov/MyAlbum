package com.rojer_ko.myalbum.presentation.albums.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.data.room.SavedAlbum
import com.rojer_ko.myalbum.presentation.albums.viewmodel.AlbumViewModel
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import com.rojer_ko.myalbum.presentation.converters.ErrorStringConverter
import com.rojer_ko.myalbum.presentation.converters.PhotoItemConverter
import com.rojer_ko.myalbum.presentation.converters.PhotosToSavedPhotosConverter
import com.rojer_ko.myalbum.utils.Consts
import com.rojer_ko.myalbum.utils.DataResult
import com.rojer_ko.myalbum.utils.Errors
import com.rojer_ko.myalbum.utils.showToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_album.*
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumFragment : BaseFragment() {

    override val layout = R.layout.fragment_album
    override val toolbarRes = R.id.album_toolbar
    override val toolbarTitleRes: Nothing? = null
    override val toolbarMenuRes = R.menu.album_toolbar_menu

    private val viewModel by viewModel<AlbumViewModel>()
    private val photoOnClick = object : PhotoContainer.ItemClick {
        override fun onClick(url: String, title: String) {
            val toPhotoFragmentBundle = Bundle()
            toPhotoFragmentBundle.putString(Consts.PHOTO_URL, url)
            toPhotoFragmentBundle.putString(Consts.PHOTO_TITLE, title)
            findNavController().navigate(
                R.id.action_albumFragment_to_photoFragment,
                toPhotoFragmentBundle
            )
        }
    }
    private var albumId = -1
    private var albumTitle = ""
    private var photos: List<PhotoDTO> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getArgs()
        super.onViewCreated(view, savedInstanceState)
        observePhotosResult()
        refreshPhotos()
    }

    override fun setToolbarTitle(toolbar: Toolbar) {
        toolbar.title = albumTitle
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_album -> {
                saveAlbum()
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getArgs() {
        try {
            albumId = requireArguments().getInt(Consts.ALBUM_ID)
            albumTitle = requireArguments().getString(Consts.ALBUM_TITLE) ?: ""
        } catch (exception: IllegalStateException) {
            Log.e("AlbumFragment", exception.toString())
            showToast(Errors.UNKNOWN.text)
        }
    }

    private fun observePhotosResult() {
        viewModel.photosResult.observe(viewLifecycleOwner, {
            when (it) {
                is DataResult.Process -> {
                    album_progress_bar.visibility = View.VISIBLE
                }
                is DataResult.Success<List<PhotoDTO>> -> {
                    album_progress_bar.visibility = View.GONE
                    photos = it.data
                    val data = PhotoItemConverter.convertPhotoDTOToContainer(it.data, photoOnClick)
                    initRecyclerView(data)
                }
                is DataResult.Error -> {
                    val error = context?.let { context ->
                        ErrorStringConverter.convertToContainer(
                            context,
                            it.error
                        )
                    } ?: Errors.UNKNOWN.text
                    showToast(error)
                    album_progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun refreshPhotos() {
        if (albumId != -1) {
            viewModel.getPhotos(albumId)
        }
    }

    private fun initRecyclerView(items: List<PhotoContainer>) {

        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        album_recyclerview.apply {
            layoutManager = LinearLayoutManager(
                activity?.applicationContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = groupAdapter
        }
    }

    private fun saveAlbum() {
        if (albumId != -1) {
            val album = SavedAlbum(albumId, albumTitle)
            viewModel.insertAlbum(album)
            showToast(getString(R.string.album_saved))
            if (photos.isNotEmpty()) {
                val savedPhotos = PhotosToSavedPhotosConverter.convert(photos)
                viewModel.insertPhotos(savedPhotos)
            } else {
                showToast(getString(R.string.photos_not_loaded))
            }
        }
    }
}