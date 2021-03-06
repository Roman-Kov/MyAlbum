package com.rojer_ko.myalbum.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.rojer_ko.myalbum.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavController()
    }

    private fun initNavController() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        main_bottom_navigation.setupWithNavController(navController)
    }
}