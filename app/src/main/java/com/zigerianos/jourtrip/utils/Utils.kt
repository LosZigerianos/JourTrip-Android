package com.zigerianos.jourtrip.utils

import android.graphics.Color
import java.util.*
import java.text.Normalizer


object Utils {
    val colors = listOf(
        "#76B772", // green
        "#E3844C", // orange
        "#6690C4", // blue
        "#E44B43", // red
        "#7d669e", // purple
        "#e0ab18", // mustard
        "#f092b0", // pink
        "#b35900" // brown
    )

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

        val randomNumber = Random().nextInt(colors.size)

        return Color.parseColor(colors[randomNumber])
    }

    fun getBasicColorByString(string: String): Int {
        val normalizeString = Normalizer.normalize(string, Normalizer.Form.NFD)
        val cleanedString = normalizeString.replace("[^\\p{ASCII}]".toRegex(), "")

        return when(cleanedString.toLowerCase().first()) {
            //in 'a'..'d' -> Color.parseColor(colors[0])
            'a', 'i', 'p', 'x' -> Color.parseColor(colors[0])
            'b', 'j', 'q', 'y' -> Color.parseColor(colors[1])
            'c', 'k', 'r', 'z' -> Color.parseColor(colors[2])
            'd', 'l', 's' -> Color.parseColor(colors[3])
            'e', 'm', 't' -> Color.parseColor(colors[4])
            'f', 'n', 'u' -> Color.parseColor(colors[5])
            'g', 'o', 'v' -> Color.parseColor(colors[6])
            else -> Color.parseColor(colors[7])
        }
    }
}