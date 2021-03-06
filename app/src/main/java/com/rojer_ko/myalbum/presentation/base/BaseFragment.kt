package com.rojer_ko.myalbum.presentation.base

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val layout: Int
    abstract val toolbarRes: Int
    abstract val toolbarTitleRes: Int?
    abstract val toolbarMenuRes: Int?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbarMenuRes?.let {
            inflater.inflate(it, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }
    }

    private fun setToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById(toolbarRes)
        setToolbarTitle(toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbarMenuRes?.let {
            this.setHasOptionsMenu(true)
        }
    }

    open fun setToolbarTitle(toolbar: Toolbar) {
        toolbarTitleRes?.let {
            toolbar.title = resources.getString(it)
        }
    }
}