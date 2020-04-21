package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.Prototype
import kotlin.reflect.KClass
import com.badlogic.gdx.math.Circle as MathCircle

open class Circle: Shape(), Prototype<Circle> {

    private val shape2d = MathCircle()

    var radius: Float = 0f

    override fun getType(): KClass<out Shape2D> = shape2d::class

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