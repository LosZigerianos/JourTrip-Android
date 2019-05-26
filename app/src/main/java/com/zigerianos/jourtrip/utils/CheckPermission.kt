package com.zigerianos.jourtrip.utils

import androidx.core.app.ActivityCompat
import android.widget.Toast
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class CheckPermission {

    companion object {
        const val TAG_PERMISSION_CODE = 111

        const val TAG_MESSAGE_PERMISSION_READ_EXTERNAL_STORAGE = "Storage permission allows us to access gallery data. Please allow in App Settings for additional functionality."
        const val TAG_MESSAGE_PERMISSION_CAMERA = "Camera permission allows us to access camera. Please allow in App Settings for additional functionality."
        const val TAG_MESSAGE_PERMISSION_LOCATION = "Location permission allows us to access location data. Please allow in App Settings for additional functionality."

        fun checkPermission(activity: Activity, permission: String) : Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val result = ContextCompat.checkSelfPermission(activity, permission)
                return result == PackageManager.PERMISSION_GRANTED
            }

            return true
        }

        fun requestPermission(activity: Activity, code: Int, permission: String, message: String) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                // todo: retocar
                Toast.makeText(
                    activity,
                    message,
                    Toast.LENGTH_LONG
                ).show()

            } else {

                ActivityCompat.requestPermissions(activity, arrayOf(permission), code)
            }
        }
    }

}