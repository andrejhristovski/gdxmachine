package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.Prototype
import kotlin.reflect.KClass
import com.badlogic.gdx.math.Circle as Circle2D

open class Circle: Shape(), Prototype<Circle> {

    private val shape2d = Circle2D()

    var radius: Float = 0f

    override fun getShapeType(): KClass<out Shape> = Circle::class

    override fun getShape2D(): Shape2D {
        shape2d.set(x, y, radius)
        return shape2d
    }

    override fun copy(): Circle {
        val circle = Circle()
        circle.inherit(this)
        return circle
    }

    override fun inherit(obj: Circle) {
        radius = obj.radius
        style = obj.style
        color = obj.color
        super.inherit(obj)
    }
}