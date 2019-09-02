package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.g2d.TextureRegion

abstract class Drawable(val type: Type) {

    enum class Type { SPRITE, SHAPE }

    var x: Float = 0f
    var y: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var pivotX: Float = .5f
    var pivotY: Float = .5f
    var rotation: Float = 0f
    var visible: Boolean = true
    var alpha: Float = 1f
}