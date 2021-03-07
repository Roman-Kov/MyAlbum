package com.rojer_ko.myalbum.presentation.albums.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import com.rojer_ko.myalbum.utils.Consts
import com.rojer_ko.myalbum.utils.Errors
import com.rojer_ko.myalbum.utils.showToast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : BaseFragment() {
    override val layout = R.layout.fragment_photo
    override val toolbarRes = R.id.photo_toolbar
    override val toolbarTitleRes: Nothing? = null
    override val toolbarMenuRes: Nothing? = null

    private var photoUrl: String? = null

    override fun setToolbarTitle(toolbar: Toolbar) {
        try {
            val fullTitle = requireArguments().getString(Consts.PHOTO_TITLE)
            toolbar.title = fullTitle
        } catch (exception: IllegalStateException) {
            Log.e("AlbumFragment", exception.toString())
            showToast(Errors.UNKNOWN.text)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        loadPhoto()
    }

    private fun getArgs() {
        try {
            photoUrl = requireArguments().getString(Consts.PHOTO_URL)
        } catch (exception: IllegalStateException) {
            Log.e("AlbumFragment", exception.toString())
            showToast(Errors.UNKNOWN.text)
        }
    }

    private fun loadPhoto() {
        Picasso.get()
            .load(photoUrl)
            .placeholder(R.drawable.ic_baseline_placeholder)
            .error(R.drawable.ic_baseline_broken_image)
            .into(photo_fullsize)
    }
}