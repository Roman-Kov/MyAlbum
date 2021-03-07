package com.rojer_ko.myalbum.presentation.albums.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.data.model.PhotoDTO
import com.rojer_ko.myalbum.presentation.albums.viewmodel.AlbumViewModel
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import com.rojer_ko.myalbum.presentation.converters.ErrorStringConverter
import com.rojer_ko.myalbum.presentation.converters.PhotoItemConverter
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
            showToast(url)
        }
    }
    private var albumId = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        observePhotosResult()
        refreshPhotos()
    }

    override fun setToolbarTitle(toolbar: Toolbar) {
        try {
            val fullTitle = requireArguments().getString(Consts.ALBUM_TITLE)
            toolbar.title = fullTitle
        } catch (exception: IllegalStateException) {
            Log.e("AlbumFragment", exception.toString())
            showToast(Errors.UNKNOWN.text)
        }
    }

    private fun getArgs() {
        try {
            albumId = requireArguments().getInt(Consts.ALBUM_ID)
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
                    val data = PhotoItemConverter.convertToContainer(it.data, photoOnClick)
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
}