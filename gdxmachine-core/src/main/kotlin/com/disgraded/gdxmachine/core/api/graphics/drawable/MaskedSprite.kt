package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class MaskedSprite : Sprite {

    private var mask: TextureRegion

    constructor(texture: Texture, mask: Texture): super(texture) {
        this.mask = TextureRegion(mask)
    }

    constructor(textureRegion: TextureRegion, mask: Texture): super(textureRegion) {
        this.mask = TextureRegion(mask)
    }

    constructor(texture: Texture, mask: TextureRegion): super(texture) {
        this.mask = mask
    }

    constructor(textureRegion: TextureRegion, mask: TextureRegion): super(textureRegion) {
        this.mask = mask
    }

    override fun getType(): String = "masked_sprite"

    fun setMask(mask: Texture) {
        this.mask = TextureRegion(mask)
    }

    fun setMask(mask: TextureRegion) {
        this.mask = mask
    }

    fun getMask() : TextureRegion {
        return mask
    }
}