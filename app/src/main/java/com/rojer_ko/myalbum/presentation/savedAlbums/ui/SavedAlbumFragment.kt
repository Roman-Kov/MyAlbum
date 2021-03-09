package com.rojer_ko.myalbum.presentation.savedAlbums.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import com.rojer_ko.myalbum.presentation.converters.PhotoItemConverter
import com.rojer_ko.myalbum.presentation.savedAlbums.viewmodel.SavedAlbumViewModel
import com.rojer_ko.myalbum.utils.Consts
import com.rojer_ko.myalbum.utils.Errors
import com.rojer_ko.myalbum.utils.showToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_saved_album.*
import org.koin.android.viewmodel.ext.android.viewModel

class SavedAlbumFragment : BaseFragment() {
    override val layout = R.layout.fragment_saved_album
    override val toolbarRes = R.id.saved_album_toolbar
    override val toolbarTitleRes: Int? = null
    override val toolbarMenuRes: Int? = null

    private val viewModel by viewModel<SavedAlbumViewModel>()
    private var albumId = -1
    private var albumTitle = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getArgs()
        super.onViewCreated(view, savedInstanceState)
        observeSavedPhotos()
    }

    override fun setToolbarTitle(toolbar: Toolbar) {
        toolbar.title = albumTitle
    }

    private fun getArgs() {
        try {
            albumId = requireArguments().getInt(Consts.ALBUM_ID)
            albumTitle = requireArguments().getString(Consts.ALBUM_TITLE) ?: ""
        } catch (exception: IllegalStateException) {
            Log.e("SavedAlbumFragment", exception.toString())
            showToast(Errors.UNKNOWN.text)
        }
    }

    private fun observeSavedPhotos() {
        if (albumId != -1) {
            viewModel.getSavedPhotos(albumId).observe(viewLifecycleOwner, {
                val data = PhotoItemConverter.convertSavedPhotoToContainer(it)
                initRecyclerView(data)
            })
        }
    }

    private fun initRecyclerView(items: List<SavedPhotoContainer>) {

        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        saved_album_recyclerview.apply {
            layoutManager = LinearLayoutManager(
                activity?.applicationContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = groupAdapter
        }
    }
}