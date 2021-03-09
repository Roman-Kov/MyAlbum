package com.rojer_ko.myalbum.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDeepLinkBuilder
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.presentation.main.MainActivity
import com.rojer_ko.myalbum.utils.Errors

class LocationService : Service() {
    companion object {
        const val LOCATION_NOTIFICATION_CHANNEL_ID = "app_location_notifications"
        const val LOCATION_SERVICE_ID = 201
        const val ACTION_START_LOCATION_SERVICE = "start_location_service"
        const val ACTION_STOP_LOCATION_SERVICE = "stop_location_service"
        val locationServiceState = MutableLiveData(false)
        val location = MutableLiveData<Pair<Double, Double>>()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.lastLocation.let {
                val latitude = it.latitude
                val longitude = it.longitude
                location.postValue(Pair(latitude, longitude))
                println("$latitude, $longitude")
            }
        }
    }
    private lateinit var mp: MediaPlayer

    override fun onCreate() {
        super.onCreate()

        initMusic()
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException(Errors.NOT_IMPLEMENTED.text)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when (it) {
                ACTION_START_LOCATION_SERVICE -> startLocationService()
                ACTION_STOP_LOCATION_SERVICE -> stopLocationService()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopLocationService()
        super.onDestroy()
    }

    private fun startLocationService() {

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.main_navigation_graph)
            .setDestination(R.id.location_fragment)
            .createPendingIntent()

        val notificationManager: NotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                LOCATION_NOTIFICATION_CHANNEL_ID,
                "location_service",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "location_service"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            val channel =
                notificationManager.getNotificationChannel(LOCATION_NOTIFICATION_CHANNEL_ID)
            channel.canBypassDnd()
        }
        val title = getString(R.string.location_service_notification_title)
        val text = getString(R.string.location_service_notification_text)
        val notificationBuilder = NotificationCompat.Builder(this, LOCATION_NOTIFICATION_CHANNEL_ID)
        notificationBuilder.apply {
            setSmallIcon(R.drawable.ic_baseline_location)
            setContentTitle(title)
            setContentText(text)
            setContentIntent(pendingIntent)
            setAutoCancel(false)
            priority = NotificationManager.IMPORTANCE_MAX
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val locationRequest = LocationRequest.create()
        locationRequest.apply {
            interval = 4000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        LocationServices.getFusedLocationProviderClient(applicationContext).requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        startForeground(LOCATION_SERVICE_ID, notificationBuilder.build())
        locationServiceState.postValue(true)
        mp.start()
    }

    private fun stopLocationService() {
        LocationServices.getFusedLocationProviderClient(applicationContext)
            .removeLocationUpdates(locationCallback)
        locationServiceState.postValue(false)
        location.postValue(Pair(0.0, 0.0))
        stopMusic()
        stopForeground(true)
        stopSelf()
    }

    private fun initMusic() {
        mp = MediaPlayer.create(applicationContext, R.raw.music).apply {
            isLooping = true
        }
    }

    private fun stopMusic() {
        if (mp.isPlaying) {
            mp.pause()
            mp.seekTo(0)
        }
    }
}