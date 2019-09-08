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

    constructor(texture: Texture, maskRegion: TextureRegion): super(texture) {
        mask = maskRegion
    }

    constructor(textureRegion: TextureRegion, maskRegion: TextureRegion): super(textureRegion) {
        mask = maskRegion
    }

    fun setMask(mask: Texture) {
        this.mask = TextureRegion(mask)
    }

    fun setMask(maskRegion: TextureRegion) {
        this.mask = maskRegion
    }

    fun getMask() : TextureRegion {
        return mask
    }
}