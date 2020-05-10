package com.disgraded.gdxmachine.framework.core.graphics.utils

import com.badlogic.gdx.math.Vector2

open class Transform2D {

    private val vector2 = Vector2()

    var x: Float = 0f
    var y: Float = 0f
    var z: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var angle: Float = 0f
        set(value) { field = value % 360 }

    var anchorX: Float = .5f
        set(value) {
            field = when {
                value > 1f -> 1f
                value < 0f -> 0f
                else -> value
            }
        }
    var anchorY: Float = .5f
        set(value) {
            field = when {
                value > 1f -> 1f
                value < 0f -> 0f
                else -> value
            }
        }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setScale(value: Float) {
        scaleX = value
        scaleY = value
    }

    fun setScale(x: Float, y: Float) {
        scaleX = x
        scaleY = y
    }

    fun setAnchor(value: Float) {
        anchorX = value
        anchorY = value
    }

    fun setAnchor(x: Float, y: Float) {
        anchorX = x
        anchorY = y
    }

    fun apply(base: Transform2D, ref: Transform2D) {
        vector2.set(base.x + ref.x, base.y + ref.y)
        vector2.sub(ref.x, ref.y).rotate(ref.angle).add(ref.x, ref.y)
        setPosition(vector2.x, vector2.y)
        angle = base.angle + ref.angle
        setScale(base.scaleX * ref.scaleX, base.scaleY * ref.scaleY)
        z = base.z + ref.z
    }

    fun set(transform2D: Transform2D) {
        x = transform2D.x
        y = transform2D.y
        z = transform2D.z
        scaleX = transform2D.scaleX
        scaleY = transform2D.scaleY
        anchorX = transform2D.anchorX
        anchorY = transform2D.anchorY
        angle = transform2D.angle

    }
}