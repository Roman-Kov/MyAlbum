package com.rojer_ko.myalbum.presentation.ui

import android.os.Bundle
import com.rojer_ko.myalbum.R

class AlbumsFragment : BaseFragment() {

    override val layout = R.layout.fragment_albums
    override val toolbarRes = R.id.albums_toolbar
    override val toolbarTitleRes = R.string.albums
    override val toolbarMenuRes: Nothing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}