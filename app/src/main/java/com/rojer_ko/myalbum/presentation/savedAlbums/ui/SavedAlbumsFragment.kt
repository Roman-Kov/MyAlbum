package com.rojer_ko.myalbum.presentation.savedAlbums.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.data.room.SavedAlbum
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import com.rojer_ko.myalbum.presentation.savedAlbums.viewmodel.SavedAlbumsViewModel
import com.rojer_ko.myalbum.utils.Consts
import com.rojer_ko.myalbum.utils.showToast
import kotlinx.android.synthetic.main.fragment_saved_albums.*
import org.koin.android.viewmodel.ext.android.viewModel

class SavedAlbumsFragment : BaseFragment() {
    override val layout = R.layout.fragment_saved_albums
    override val toolbarRes = R.id.saved_albums_toolbar
    override val toolbarTitleRes = R.string.saved_albums
    override val toolbarMenuRes: Int? = null

    private val viewModel by viewModel<SavedAlbumsViewModel>()
    private val itemRemoveListener = object : SavedAlbumsAdapter.OnItemRemoveListener {
        override fun itemRemove(id: Int, position: Int) {
            viewModel.deleteAlbum(SavedAlbum(id, ""))
            showToast(getString(R.string.album_removed))
        }
    }
    private val itemClickListener = object : SavedAlbumsAdapter.OnItemClickListener {
        override fun onClick(id: Int, title: String) {

            val toSavedAlbumFragmentBundle = Bundle()
            toSavedAlbumFragmentBundle.putInt(Consts.ALBUM_ID, id)
            toSavedAlbumFragmentBundle.putString(Consts.ALBUM_TITLE, title)
            findNavController().navigate(
                R.id.action_saved_albums_fragment_to_savedAlbumFragment,
                toSavedAlbumFragmentBundle
            )
        }
    }
    private val adapter by lazy { SavedAlbumsAdapter(itemRemoveListener, itemClickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubscribeRecyclerView()
        viewModel.albums.observe(viewLifecycleOwner, {
            setDataToAdapter(it.toMutableList())
        })
    }

    private fun initSubscribeRecyclerView() {
        saved_albums_recyclerview.adapter = adapter
        saved_albums_recyclerview.layoutManager = LinearLayoutManager(context)
        val callback = SwipeToDeleteCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(saved_albums_recyclerview)
    }

    private fun setDataToAdapter(albums: MutableList<SavedAlbum>) {
        adapter.setData(albums)
    }
}