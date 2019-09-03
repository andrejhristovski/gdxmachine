package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class RenderBatch2D(private val shaderContainer: ShaderContainer) : SpriteBatch(1500) {

    fun draw(drawable: Drawable) {
        if(!drawable.visible) return
        when(drawable.type) {
            Drawable.Type.SPRITE -> drawSprite(drawable as Sprite)
            Drawable.Type.SHAPE -> drawShape(drawable as Shape)
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

    private fun drawShape(shape: Shape) {
        TODO("implement drawing call for shape type of drawable")
    }

}