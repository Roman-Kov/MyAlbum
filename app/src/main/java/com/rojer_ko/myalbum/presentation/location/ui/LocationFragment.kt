package com.rojer_ko.myalbum.presentation.location.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment: BaseFragment() {

    override val layout = R.layout.fragment_location
    override val toolbarRes = R.id.location_toolbar
    override val toolbarTitleRes = R.string.location
    override val toolbarMenuRes: Int? = null
    private var isLocated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locate()
    }

    private fun locate() {
        start_location_btn.setOnClickListener {
            when (isLocated) {
                true -> {
                    btnToStart()
                    stopLocate()
                }
                false -> {
                    btnToStop()
                    startLocate()
                }
            }
            isLocated = isLocated.not()
        }
    }

    private fun btnToStart() {
        start_location_btn.apply {
            text = resources.getString(R.string.start_locate)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_play_arrow_24)
        }
    }

    private fun btnToStop() {
        start_location_btn.apply {
            text = resources.getString(R.string.stop_locate)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_stop_24)
        }
    }

    private fun startLocate() {}

    private fun stopLocate() {}
}