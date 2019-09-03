package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.Color as GdxColor

class Color {

    companion object {
        fun toGdxColor(color: Color): GdxColor {
            return GdxColor(color.r, color.g, color.b, color.alpha)
        }

        fun fromGdxColor(gdxColor: GdxColor): Color {
            return Color(gdxColor.r, gdxColor.g, gdxColor.b, gdxColor.a)
        }
    }

    private var r : Float = 0f
    private var g : Float = 0f
    private var b : Float = 0f
    var alpha : Float = 1f


    constructor(r : Float, g : Float, b: Float, alpha : Float = 1f) {
        set(r, g, b, alpha)
    }

    constructor(hexColor : String, alpha : Float = 1f) {
        set(hexColor, alpha)
    }

    fun set(hex: String, alpha: Float) {
        if (hex.length != 7 || hex.first() != '#') {
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
        this.alpha = a
    }
}