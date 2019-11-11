package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import kotlin.reflect.KClass

class Shape<T : Shape2D>(val shape2D: T) : Drawable2D() {
    enum class Style {
        FILLED, LINE, POINT
    }

    var style = Style.FILLED
    var color = Color.WHITE

    fun getType(): KClass<out T> = shape2D::class

}