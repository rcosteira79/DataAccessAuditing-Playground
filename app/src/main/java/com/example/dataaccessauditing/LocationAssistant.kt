package com.example.dataaccessauditing

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat

class LocationAssistant(private val context: Context, private val activity: Activity) {

    companion object {
        private const val LOG_TAG = "LocationAssistant"
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    private val locationManager: LocationManager by lazy {
        context.getSystemService(LocationManager::class.java)
    }

    fun requestLocationUpdates() {
        if (this.checkHasFineLocationPermission()) {
            val minTimeMs = 2000L
            val minDistanceM = 1f

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTimeMs,
                minDistanceM
            ) { Log.d(LOG_TAG, "Location altitude: ${it.altitude}") }
        }
    }

    private fun checkHasFineLocationPermission(): Boolean {
        return if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            if (shouldExplainFineLocationPermission()) {
                launchFineLocationPermissionRequestDialog()
            } else {
                requestFineLocationPermission()
            }

            false
        } else {
            true
        }
    }

    private val fineLocationPermission
        get() = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun shouldExplainFineLocationPermission(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun launchFineLocationPermissionRequestDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Hi there")
            .setMessage("Can I haz location?")
            .setPositiveButton("Sure?") { _, _ ->
                requestFineLocationPermission()
            }
            .create()
            .show()
    }

    private fun requestFineLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }
}