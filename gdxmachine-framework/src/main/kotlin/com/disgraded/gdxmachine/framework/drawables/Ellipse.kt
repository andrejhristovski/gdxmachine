package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.Prototype
import kotlin.reflect.KClass
import com.badlogic.gdx.math.Ellipse as Ellipse2D

open class Ellipse: Shape(), Prototype<Ellipse> {

    private val shape2d = Ellipse2D()

    var width: Float = 0f
    var height: Float = 0f

    override fun getShapeType(): KClass<out Shape> = Ellipse::class

    override fun getShape2D(): Shape2D {
        shape2d.set(x, y, width, height)
        return shape2d
    }

    override fun copy(): Ellipse {
        val ellipse = Ellipse()
        ellipse.inherit(this)
        return ellipse
    }

    override fun inherit(obj: Ellipse) {
        width = obj.width
        height = obj.height
        style = obj.style
        color = obj.color
        super.inherit(obj)
    }
}