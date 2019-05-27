package com.zigerianos.jourtrip.utils

import android.graphics.Color
import java.util.*

object Utils {
    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}