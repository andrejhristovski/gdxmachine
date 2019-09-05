package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.core.api.graphics.Color

class Sprite : Drawable(Type.SPRITE) {

    var colorLeftTop = Color("#ffffff")
    var colorRightTop = Color("#ffffff")
    var colorRightBottom = Color("#ffffff")
    var colorLeftBottom = Color("#ffffff")

    var textureRegion: TextureRegion? = null
    var flippedX = false
    var flippedY = false

    fun setColor(color: Color) {
        colorLeftTop = color
        colorRightTop = color
        colorRightBottom = color
        colorLeftBottom = color
    }

}