package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class RenderBatch2D(private val shaderContainer: ShaderContainer) : SpriteBatch(1500) {

    private var lastMask : TextureRegion? = null

    fun draw(drawable: Drawable) {
        if(!drawable.visible) return
        when(drawable.type) {
            Drawable.Type.SPRITE -> drawSprite(drawable as Sprite)
            Drawable.Type.MASKED_SPRITE -> drawMaskedSprite(drawable as MaskedSprite)
        }
    }

    private fun drawSprite(sprite: Sprite) {
        if(sprite.getTexture() == null) return

        val texture = sprite.getTexture()!!
        val sizeX = texture.regionWidth.toFloat()
        val sizeY = texture.regionHeight.toFloat()
        val originX = sizeX * sprite.scaleX * sprite.pivotX
        val originY = sizeY * sprite.scaleY * sprite.pivotY
        val posX = sprite.x - originX
        val posY = sprite.y - originY

        shader = shaderContainer.get(sprite.type, sprite.effect)
        color = Color.toGdxColor(sprite.color)
        shader.setUniformf("u_intensity", sprite.intensity)

        this.draw(texture, posX, posY, originX, originY, sizeX, sizeY, sprite.scaleX, sprite.scaleY, sprite.rotation)
    }

    private fun drawMaskedSprite(sprite: MaskedSprite) {
        if(sprite.getTexture() == null) return
        if(sprite.getMask() == null) return

        val texture = sprite.getTexture()!!
        val sizeX = texture.regionWidth.toFloat()
        val sizeY = texture.regionHeight.toFloat()
        val originX = sizeX * sprite.scaleX * sprite.pivotX
        val originY = sizeY * sprite.scaleY * sprite.pivotY
        val posX = sprite.x - originX
        val posY = sprite.y - originY

        shader = shaderContainer.get(sprite.type, sprite.effect)
        color = Color.toGdxColor(sprite.color)
        shader.setUniformf("u_intensity", sprite.intensity)

        if(sprite.getMask() !== lastMask) {
            lastMask = sprite.getMask()
            sprite.getMask()!!.texture.bind(1)
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
            shader.setUniformi("u_mask", 1)
        }

        this.draw(texture, posX, posY, originX, originY, sizeX, sizeY, sprite.scaleX, sprite.scaleY, sprite.rotation)
    }

}