package com.rojer_ko.myalbum.presentation.savedAlbums.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.data.room.SavedAlbum
import kotlinx.android.synthetic.main.item_saved_albums.view.*

class SavedAlbumsAdapter(
    private val itemRemoveListener: OnItemRemoveListener
) : RecyclerView.Adapter<SavedAlbumsAdapter.SubscribeViewHolder>(),
    SwipeToDeleteCallback.ItemTouchHelperAdapter {

    private var albums: MutableList<SavedAlbum> = mutableListOf()

    fun setData(data: MutableList<SavedAlbum>) {
        albums = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SubscribeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_saved_albums, parent, false)
    )

    override fun onBindViewHolder(holder: SubscribeViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount() = albums.size

    override fun onItemDismiss(position: Int) {
        val albumId = albums[position].id
        itemRemoveListener.itemRemove(albumId, position)
        albums.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class SubscribeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(album: SavedAlbum) {
            itemView.saved_album_title.text = album.title
        }
    }

    interface OnItemRemoveListener {
        fun itemRemove(id: Int, position: Int)
    }
}