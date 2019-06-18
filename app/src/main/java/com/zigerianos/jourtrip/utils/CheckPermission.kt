package com.zigerianos.jourtrip.utils

import androidx.core.app.ActivityCompat
import android.widget.Toast
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class CheckPermission {

    companion object {
        const val TAG_PERMISSION_READ_EXTERNAL_STORAGE = 110
        const val TAG_PERMISSION_CAMERA = 111
        const val TAG_PERMISSION_LOCATION= 112

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

        fun requestPermission(fragment: Fragment, code: Int, permission: String, message: String) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                Toast.makeText(
                    fragment.context,
                    message,
                    Toast.LENGTH_LONG
                ).show()

            } else {
                fragment.requestPermissions(arrayOf(permission), code)
            }
        }
    }

}