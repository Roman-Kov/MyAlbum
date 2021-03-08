package com.rojer_ko.myalbum.presentation.location.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.presentation.base.BaseFragment
import com.rojer_ko.myalbum.presentation.main.MainActivity
import com.rojer_ko.myalbum.service.LocationService
import com.rojer_ko.myalbum.utils.showToast
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : BaseFragment() {

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 1
    }

    override val layout = R.layout.fragment_location
    override val toolbarRes = R.id.location_toolbar
    override val toolbarTitleRes = R.string.location
    override val toolbarMenuRes: Int? = null
    private var isLocated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locate()
        observeLocationServiceState()
        observeLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSIONS_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocate()
            } else {
                showToast(getString(R.string.permissions_denied))
            }
        }
    }

    private fun observeLocationServiceState() {
        LocationService.locationServiceState.observe(viewLifecycleOwner,
            {
                isLocated = it
                when (isLocated) {
                    true -> btnToStop()
                    false -> btnToStart()
                }
            })
    }

    private fun observeLocation() {
        LocationService.location.observe(viewLifecycleOwner,
            {
                val latitude = it.first
                val longitude = it.second
                val coordinates = "$latitude, $longitude"
                location_textview.text = coordinates
            })
    }

    private fun locate() {
        start_location_btn.setOnClickListener {
            when (isLocated) {
                true -> stopLocate()
                false -> startLocate()
            }
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

    private fun startLocate() {
        if (checkPermissions()) {
            activity?.apply {
                val intent = Intent(applicationContext, LocationService::class.java)
                intent.action = LocationService.ACTION_START_LOCATION_SERVICE
                startService(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun stopLocate() {
        activity?.apply {
            val intent = Intent(applicationContext, LocationService::class.java)
            intent.action = LocationService.ACTION_STOP_LOCATION_SERVICE
            startService(intent)
        }
    }

    private fun checkPermissions(): Boolean {
        (activity as MainActivity).let {
            return !(ActivityCompat.checkSelfPermission(
                it.applicationContext,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                it.applicationContext,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        }
    }

    private fun requestPermission() {
        (activity as MainActivity).let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSIONS_REQUEST_CODE
            )
        }
    }
}