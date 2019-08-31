package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class RenderBatch(private val shaderContainer: ShaderContainer) : SpriteBatch(2000) {

    fun draw(drawable: Drawable) {

        super.draw(drawable.textureRegion, drawable.x, drawable.y, drawable.pivotX, drawable.pivotY,
                drawable.sizeX, drawable.sizeY, drawable.scaleX, drawable.scaleY, drawable.rotation)
    }

}