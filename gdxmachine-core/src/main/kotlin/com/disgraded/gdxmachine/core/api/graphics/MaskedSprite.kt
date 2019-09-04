package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class MaskedSprite : Drawable(Type.MASKED_SPRITE) {

    private var texture: TextureRegion? = null
    private var mask: TextureRegion? = null

    fun setTexture(textureRegion: TextureRegion) {
        texture = textureRegion
    }

    fun setTexture(texture: Texture) {
        this.texture = TextureRegion(texture)
    }

    fun getTexture(): TextureRegion? = texture

    fun setMask(texture: Texture) {
        mask = TextureRegion(texture)
    }

    fun setMask(textureRegion: TextureRegion) {
        mask = textureRegion
    }

    fun getMask(): TextureRegion? = mask
}