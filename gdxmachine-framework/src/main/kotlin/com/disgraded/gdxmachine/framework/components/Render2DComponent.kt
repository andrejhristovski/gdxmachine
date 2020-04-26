package com.disgraded.gdxmachine.framework.components

import com.badlogic.gdx.math.Vector2
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.engine.Component
import com.disgraded.gdxmachine.framework.drawables.*
import com.disgraded.gdxmachine.framework.utils.Transform2D

class Render2DComponent: Component {

    private val drawables = hashMapOf<String, Drawable2D>()
    private val absoluteMap = hashMapOf<String, Drawable2D>()
    private val drawableList = arrayListOf<Drawable2D>()

    private var tempVec2 = Vector2()

    fun <T: Drawable2D> add(key:String, drawable2D: T): T {
        if (drawables.containsKey(key)) {
            throw RuntimeException("The key $key already exist in drawable list")
        }
        drawables[key] = drawable2D
        createAbsolute(key, drawable2D)
        return drawable2D
    }

    fun remove(key: String) {
        if (!drawables.containsKey(key)) {
            throw RuntimeException("The key $key doesn't exist")
        }
        drawables.remove(key)
        absoluteMap.remove(key)
    }

    fun get(key: String, absolute: Boolean = false): Drawable2D {
        if (!drawables.containsKey(key)) {
            throw RuntimeException("The key $key doesn't exist")
        }
        if (absolute) {
            return absoluteMap[key]!!
        }
        return drawables[key]!!
    }

    fun getAll(absolute: Boolean = false): ArrayList<Drawable2D> {
        drawableList.clear()
        val map = if (absolute) absoluteMap else drawables
        for ((_, drawable) in map) {
            drawableList.add(drawable)
        }
        return drawableList
    }

    fun update(transform2D: Transform2D) {
        val deltaTime = Core.graphics.getDeltaTime()
        for (key in drawables.keys) {
            val drawable = drawables[key]!!
            val absolute = absoluteMap[key]!!
            when (absolute.getType()) {
                Sprite::class -> (absolute as Sprite).inherit(drawable as Sprite)
                Text::class -> (absolute as Text).inherit(drawable as Text)
                Shape::class -> when ((absolute as Shape).getShapeType()) {
                    Rectangle::class -> (absolute as Rectangle).inherit(drawable as Rectangle)
                    Circle::class -> (absolute as Circle).inherit(drawable as Circle)
                    Ellipse::class -> (absolute as Ellipse).inherit(drawable as Ellipse)
                    Polygon::class -> (absolute as Polygon).inherit(drawable as Polygon)
                }
            }
            applyTransform(drawable, absolute, transform2D)
        }
    }

    private fun <T: Drawable2D> createAbsolute(key: String, drawable2D: T) {

        val drawable: Drawable2D = when(drawable2D.getType()) {
            Sprite::class -> (drawable2D as Sprite).copy()
            Text::class -> (drawable2D as Text).copy()
            Shape::class -> when((drawable2D as Shape).getShapeType()) {
                Rectangle::class -> (drawable2D as Rectangle).copy()
                Circle::class -> (drawable2D as Circle).copy()
                Ellipse::class -> (drawable2D as Ellipse).copy()
                Polygon::class -> (drawable2D as Polygon).copy()
                else -> throw RuntimeException("Shape type ${drawable2D.getShapeType()} is not supported")
            }
            else -> throw RuntimeException("Type of drawable ${drawable2D.getType()} is not supported")
        }
        absoluteMap[key] = drawable
    }

    private fun applyTransform(drawable: Drawable2D, absolute: Drawable2D, transform2D: Transform2D) {
        tempVec2.set(drawable.x + transform2D.x, drawable.y + transform2D.y)
        tempVec2.sub(transform2D.x, transform2D.y).rotate(transform2D.angle).add(transform2D.x, transform2D.y)
        absolute.setPosition(tempVec2.x, tempVec2.y)
        absolute.angle = transform2D.angle + drawable.angle
        absolute.setScale(drawable.scaleX * transform2D.scaleX, drawable.scaleY * transform2D.scaleY)
        absolute.z += 1f / Int.MAX_VALUE.toFloat() * transform2D.z
    }
}