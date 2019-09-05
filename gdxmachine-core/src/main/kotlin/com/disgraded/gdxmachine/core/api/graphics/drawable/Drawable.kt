package com.disgraded.gdxmachine.core.api.graphics.drawable

abstract class Drawable(val type: Type) {

    enum class Type(val key: String) {
        SPRITE("sprite")
    }

    var x: Float = 0f
    var y: Float = 0f
    var z: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var pivotX: Float = .5f
    var pivotY: Float = .5f
    var rotation: Float = 0f
    var visible: Boolean = true
}