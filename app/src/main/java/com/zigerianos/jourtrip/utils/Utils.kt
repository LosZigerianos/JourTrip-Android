package com.zigerianos.jourtrip.utils

import android.graphics.Color
import java.util.*

object Utils {
    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun getRandomDarkColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200))
    }

    // Method (abilities: things the object can do)
    fun getBasicColor(): Int {

        /*val mColors = arrayOf(
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0" // pink
        )*/

        val mColors = arrayOf(
            "#76B772", // green
            "#E3844C", // orange
            "#6690C4", // blue
            "#E44B43", // red
            "#7d669e", // purple
            "#e0ab18", // mustard
            "#f092b0", // pink
            "#b35900" // brown
        )

        val randomNumber = Random().nextInt(mColors.size)

        return Color.parseColor(mColors[randomNumber])
    }
}