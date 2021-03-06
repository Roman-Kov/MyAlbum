package com.rojer_ko.myalbum.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.data.model.AlbumDTO
import com.rojer_ko.myalbum.presentation.converters.AlbumItemConverter
import com.rojer_ko.myalbum.presentation.converters.ErrorStringConverter
import com.rojer_ko.myalbum.presentation.viewmodel.AlbumsViewModel
import com.rojer_ko.myalbum.utils.DataResult
import com.rojer_ko.myalbum.utils.Errors
import com.rojer_ko.myalbum.utils.showToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumsFragment : BaseFragment() {

    override val layout = R.layout.fragment_albums
    override val toolbarRes = R.id.albums_toolbar
    override val toolbarTitleRes = R.string.albums
    override val toolbarMenuRes: Nothing? = null

    private val viewModel by viewModel<AlbumsViewModel>()
    private val albumOnClick = object : AlbumContainer.ItemClick {
        override fun onClick(id: Int) {
            showToast(id.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAlbumsResult()
        refreshAlbums()
    }

    private fun observeAlbumsResult() {
        viewModel.albumsResult.observe(viewLifecycleOwner, {
            when (it) {
                is DataResult.Process -> {
                    albums_progress_bar.visibility = View.VISIBLE
                }
                is DataResult.Success<List<AlbumDTO>> -> {
                    albums_progress_bar.visibility = View.GONE
                    val data = AlbumItemConverter.convertToContainer(it.data, albumOnClick)
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
                    albums_progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun refreshAlbums() {
        viewModel.getAlbums()
    }

    private fun initRecyclerView(items: List<AlbumContainer>) {

        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        albums_recyclerview.apply {
            layoutManager = LinearLayoutManager(
                activity?.applicationContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = groupAdapter
        }
    }
}