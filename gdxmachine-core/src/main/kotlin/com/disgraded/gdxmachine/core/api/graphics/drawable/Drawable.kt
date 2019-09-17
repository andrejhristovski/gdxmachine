package com.disgraded.gdxmachine.core.api.graphics.drawable

abstract class Drawable(val type: Type) {

    enum class Type { SPRITE, TEXT, LIGHT }

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