package com.example.dataaccessauditing

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.wafflecopter.multicontactpicker.MultiContactPicker

class MyContactPicker(private val context: Context, private val activity: AppCompatActivity) {

    companion object {
        private const val READ_CONTACT_PERMISSION_REQUEST = 123
    }

    fun launch() {
        if (readContactsPermission == PackageManager.PERMISSION_GRANTED) {
            MultiContactPicker.Builder(activity)
                .showPickerForResult(99)
        } else {
            requestReadContactsPermission()
        }
    }

    private val readContactsPermission
        get() = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        )

    private fun requestReadContactsPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            READ_CONTACT_PERMISSION_REQUEST
        )
    }
}
