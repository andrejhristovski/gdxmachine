package com.disgraded.gdxmachine.framework.utils

abstract class Transform2D {

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
}