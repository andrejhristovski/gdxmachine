package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class MaskedSprite : Sprite() {

    private var textureRegionMask: TextureRegion? = null

    fun setMask(texture: Texture) {
        setMask(TextureRegion(texture))
    }

    fun setMask(textureRegion: TextureRegion) {
        this.textureRegionMask = textureRegion
    }

    fun getMask(): TextureRegion {
        if (textureRegionMask == null) throw RuntimeException("") // TODO: message
        return textureRegionMask!!
    }
}