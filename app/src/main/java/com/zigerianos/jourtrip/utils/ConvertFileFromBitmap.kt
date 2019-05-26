package com.zigerianos.jourtrip.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ConvertFileFromBitmap(private val context: Context, private val bitmap: Bitmap, private val callback: (File) -> Unit) : AsyncTask<Void, Int?, String?>() {

    private var file: File? = null
    internal var path_external =
        Environment.getExternalStorageDirectory().toString() + File.separator + "temporary_file.jpg"

    override fun onPreExecute() {
        super.onPreExecute()
        // before executing doInBackground
        // update your UI
        // exp; make progressbar visible
    }

    override fun doInBackground(vararg params: Void): String? {

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        file = File(context.getCacheDir(), "temporary_file.jpg");
        try {
            val fo = FileOutputStream(file)
            fo.write(bytes.toByteArray())
            fo.flush()
            fo.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
        // back to main thread after finishing doInBackground
        // update your UI or take action after

        file?.let {
            callback.invoke(it)
        }

    }
}