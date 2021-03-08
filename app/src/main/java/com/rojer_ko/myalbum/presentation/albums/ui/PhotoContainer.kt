package com.rojer_ko.myalbum.presentation.albums.ui

import android.widget.ImageView
import com.rojer_ko.myalbum.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_photo.*

class PhotoContainer(
    private val photoTitle: String,
    private val photoUrl: String,
    private val photoThumbnailUrl: String,
    private val onClick: ItemClick
) : Item() {

    override fun getLayout() = R.layout.item_photo

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            loadPhoto(photo, photoThumbnailUrl)
            photo_title.text = photoTitle
            photo_container.setOnClickListener {
                onClick.onClick(photoUrl, photoTitle)
            }
        }
    }

    private fun loadPhoto(imageView: ImageView, url: String) {

        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_placeholder)
            .error(R.drawable.ic_baseline_broken_image)
            .into(imageView)
    }

    interface ItemClick {
        fun onClick(url: String, title: String)
    }
}