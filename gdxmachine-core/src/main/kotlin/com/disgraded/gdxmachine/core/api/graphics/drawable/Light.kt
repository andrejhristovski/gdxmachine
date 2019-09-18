package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.disgraded.gdxmachine.core.api.graphics.utils.Color

abstract class Light(val lightType: LightType): Drawable(Type.LIGHT) {

    private val colorMap = hashMapOf<Corner, Color>()

    var intensity = 1f
        set(value) { field = if (value > 1f) 1f else if(value < 0f) 0f else value }
    var radius = 0f
        set(value) { field = value % 360 }


    init {
        colorMap[Corner.TOP_LEFT] = Color.WARM_WHITE
        colorMap[Corner.TOP_RIGHT] = Color.WARM_WHITE
        colorMap[Corner.BOTTOM_LEFT] = Color.WARM_WHITE
        colorMap[Corner.BOTTOM_RIGHT] = Color.WARM_WHITE
    }

    fun setColor(color: Color) {
        colorMap[Corner.TOP_LEFT] = color
        colorMap[Corner.TOP_RIGHT] = color
        colorMap[Corner.BOTTOM_LEFT] = color
        colorMap[Corner.BOTTOM_RIGHT] = color
    }

    fun setColor(corner: Corner, color: Color) {
        when(corner) {
            Corner.TOP_LEFT -> colorMap[Corner.TOP_LEFT] = color
            Corner.TOP_RIGHT -> colorMap[Corner.TOP_RIGHT] = color
            Corner.BOTTOM_LEFT -> colorMap[Corner.BOTTOM_LEFT] = color
            Corner.BOTTOM_RIGHT -> colorMap[Corner.BOTTOM_RIGHT] = color
        }
    }

    fun getColor(): Color {
        return colorMap[Corner.TOP_LEFT]!!
    }

    fun getColor(corner: Corner): Color {
        return when(corner) {
            Corner.TOP_LEFT -> colorMap[Corner.TOP_LEFT]!!
            Corner.TOP_RIGHT -> colorMap[Corner.TOP_RIGHT]!!
            Corner.BOTTOM_LEFT -> colorMap[Corner.BOTTOM_LEFT]!!
            Corner.BOTTOM_RIGHT -> colorMap[Corner.BOTTOM_RIGHT]!!
        }
    }
}