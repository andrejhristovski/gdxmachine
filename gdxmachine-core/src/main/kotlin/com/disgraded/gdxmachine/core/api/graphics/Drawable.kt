package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.g2d.TextureRegion

object Drawable {
    lateinit var textureRegion: TextureRegion
    var visible: Boolean = true
    var x: Float = 0f
    var y: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var pivotX: Float = .5f
    var pivotY: Float = .5f
    var rotation: Float = 0f
}