package com.disgraded.gdxmachine.framework.drawables

import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader

abstract class Drawable2D : Drawable {

    override var shader: Shader? = null

    var visible: Boolean = true
    var x: Float = 0f
    var y: Float = 0f
    var z: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var anchorX: Float = .5f
    var anchorY: Float = .5f
    var angle: Float = 0f
        set(value) { field = value % 360 }

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

    override fun getOrder(): Long = z.toLong()

    override fun isExecutable(): Boolean = visible
}