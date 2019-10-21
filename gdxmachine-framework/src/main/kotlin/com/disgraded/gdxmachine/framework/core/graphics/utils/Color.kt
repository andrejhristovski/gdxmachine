package com.disgraded.gdxmachine.framework.core.graphics.utils
import com.badlogic.gdx.utils.NumberUtils

class Color {

    companion object {
        val WHITE: Color = Color("#ffffff")
        val WARM_WHITE: Color = Color("#ffcc99")
        val RED: Color = Color("#f44336")
        val PINK: Color = Color("#e91e63")
        val PURPLE: Color = Color("#9c27b0")
        val DEEP_PURPLE: Color = Color("#673ab7")
        val INDIGO: Color = Color("#3f51b5")
        val BLUE: Color = Color("#2196f3")
        val LIGHT_BLUE: Color = Color("#03a9f4")
        val CYAN: Color = Color("#00bcd4")
        val TEAL: Color = Color("#009688")
        val GREEN: Color = Color("#4caf50")
        val LIGHT_GREEN: Color = Color("#8bc34a")
        val LIME: Color = Color("#cddc39")
        val YELLOW: Color = Color("#ffeb3b")
        val AMBER: Color = Color("#ffc107")
        val ORANGE: Color = Color("#ff9800")
        val DEEP_ORANGE: Color = Color("#ff5722")
        val BROWN: Color = Color("#795548")
        val GREY: Color = Color("#9e9e9e")
        val BLUE_GREY: Color = Color("#607d8b")
        val BLACK: Color = Color("#000000")

        fun random(): Color {
            return Color(Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat(),
                    Math.random().toFloat())
        }
    }

    var r : Float = 0f
    var g : Float = 0f
    var b : Float = 0f
    var a : Float = 1f


    constructor(r : Float, g : Float, b: Float, a : Float = 1f) {
        set(r, g, b, a)
    }

    constructor(hexColor : String, alpha : Float = 1f) {
        set(hexColor, alpha)
    }

    fun set(hex: String, alpha: Float) {
        val regex = "#[0-9A-F]{6}".toRegex()
        if (!hex.toUpperCase().matches(regex)) {
            throw Exception("Hex code $hex isn't valid hex color code")
        }

        val r = Integer.valueOf(hex.substring(1, 3), 16).toFloat() / 255
        val g = Integer.valueOf(hex.substring(3, 5), 16).toFloat() / 255
        val b = Integer.valueOf(hex.substring(5, 7), 16).toFloat() / 255
        set(r, g, b, alpha)
    }

    fun set(r : Float, g : Float, b: Float, a : Float) {
        if (r > 1f || g > 1f || b > 1f || a > 1f || r < 0f || g < 0f || b < 0f || a < 0f) {
            throw RuntimeException("Invalid color values")
        }
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    fun toFloatBits(): Float {
        val color = (255 * a).toInt() shl 24 or ((255 * b).toInt() shl 16) or ((255 * g).toInt() shl 8) or (255 * r).toInt()
        return NumberUtils.intToFloatColor(color)
    }

    fun copy(): Color {
        return Color(r, g, b, a)
    }
}