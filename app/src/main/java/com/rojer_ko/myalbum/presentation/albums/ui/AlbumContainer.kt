package com.rojer_ko.myalbum.presentation.albums.ui

import com.rojer_ko.myalbum.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_albums.*

class AlbumContainer(
    private val albumTitle: String,
    private val albumId: Int,
    private val onClick: ItemClick
) : Item() {

    override fun getLayout() = R.layout.item_albums

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            album_title.text = albumTitle
            album_title.setOnClickListener {
                onClick.onClick(albumId, albumTitle)
            }
        }
    }

    interface ItemClick {
        fun onClick(id: Int, title: String)
    }
}