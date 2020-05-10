package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.Prototype
import com.badlogic.gdx.math.Rectangle as Rectangle2D
import kotlin.reflect.KClass

open class Rectangle: Shape(), Prototype<Rectangle> {

    private val shape2d = Rectangle2D()

    var width: Float = 0f
    var height: Float = 0f

    override fun getShapeType(): KClass<out Shape> = Rectangle::class

    override fun getShape2D(): Shape2D {
        shape2d.set(x, y, width, height)
        return shape2d
    }

    override fun copy(): Rectangle {
        val rect = Rectangle()
        rect.inherit(this)
        return rect
    }

    override fun inherit(obj: Rectangle) {
        width = obj.width
        height = obj.height
        style = obj.style
        color = obj.color
        super.inherit(obj)
    }
}