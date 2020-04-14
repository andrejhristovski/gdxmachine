package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.core.Prototype
import kotlin.reflect.KClass

class Shape<T : Shape2D>(val shape: T) : Drawable2D(), Prototype<Shape<T>> {
    enum class Style {
        FILLED, LINE, POINT
    }

    var style = Style.FILLED
    var color = Color.WHITE

    fun getType(): KClass<out T> = shape::class

    override fun copy(): Shape<T> {
        val shape = Shape(shape)
        shape.inherit(this)
        return shape
    }

    override fun inherit(obj: Shape<T>) {
        style = obj.style
        color = obj.color
        super.inherit(obj)
    }
}