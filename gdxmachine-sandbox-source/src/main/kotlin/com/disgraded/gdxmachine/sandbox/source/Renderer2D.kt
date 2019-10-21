package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.BatchManager
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.Renderer
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class Renderer2D: Renderer {

    private val spriteBatch = BatchManager()

    init {
        spriteBatch.addRenderer(Sprite::class, SpriteBatch())
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        return spriteBatch.execute(drawableList, layer.camera.combined)
    }

    override fun dispose() {

    }
}