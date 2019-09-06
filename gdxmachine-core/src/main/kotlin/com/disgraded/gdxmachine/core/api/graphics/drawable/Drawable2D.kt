package com.disgraded.gdxmachine.core.api.graphics.drawable

abstract class Drawable2D(val type: Type) {

    enum class Corner { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT }

    enum class Type(val key: String) {
        SPRITE("sprite")
    }

    var x: Float = 0f
    var y: Float = 0f
    var z: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var anchorX: Float = .5f
    var anchorY: Float = .5f
    var rotation: Float = 0f
        set(value) { field = value % 360 }
    var visible: Boolean = true

    fun setPosition(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setScale(value: Float) {
        scaleX = value
        scaleY = value
    }

    fun setAnchor(value: Float) {
        anchorX = value
        anchorY = value
    }
}