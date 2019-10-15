package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.graphics.Drawable
import com.disgraded.gdxmachine.framework.graphics.DrawableBatch
import com.disgraded.gdxmachine.framework.graphics.Renderer

class Renderer2D: Renderer {

    override val gpuCalls: Int = 0

    private val spriteBatch = DrawableBatch()

    init {
        spriteBatch.addRenderer(Sprite::class, SpriteDrawableHandler())
    }

    override fun render(drawableList: ArrayList<Drawable>) {
        spriteBatch.execute(drawableList)
    }

    override fun dispose() {

    }
}