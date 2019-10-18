package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.graphics.Drawable
import com.disgraded.gdxmachine.framework.graphics.BatchManager
import com.disgraded.gdxmachine.framework.graphics.Layer
import com.disgraded.gdxmachine.framework.graphics.Renderer

class Renderer2D: Renderer {

    private val spriteBatch = BatchManager()

    init {
        spriteBatch.addRenderer(Sprite::class, SpriteBatch())
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        return spriteBatch.execute(drawableList)
    }

    override fun dispose() {

    }
}