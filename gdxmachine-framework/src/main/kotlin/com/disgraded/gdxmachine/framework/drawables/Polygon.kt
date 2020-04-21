package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.math.Shape2D
import com.disgraded.gdxmachine.framework.core.Prototype
import kotlin.reflect.KClass
import com.badlogic.gdx.math.Polygon as MathPolygon

open class Polygon: Shape(), Prototype<Polygon> {

    private val shape2d = MathPolygon()

    var vertices = arrayListOf<Float>()

    override fun getType(): KClass<out Shape2D> = shape2d::class

    override fun getShape2D(): Shape2D {
        shape2d.setPosition(x, y)
        shape2d.setScale(scaleX, scaleY)
        shape2d.setOrigin(anchorX, anchorY)
        shape2d.rotation = angle
        shape2d.vertices = vertices.toFloatArray()
        return shape2d
    }

    override fun copy(): Polygon {
        val polygon = Polygon()
        polygon.inherit(this)
        return polygon
    }

    override fun inherit(obj: Polygon) {
        vertices = obj.vertices
        style = obj.style
        color = obj.color
        super.inherit(obj)
    }
}