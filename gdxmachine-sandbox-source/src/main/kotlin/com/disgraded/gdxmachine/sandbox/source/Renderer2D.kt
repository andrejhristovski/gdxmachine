package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.BatchManager
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.Renderer
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class Renderer2D(core: Core): Renderer {

    private val spriteBatch = BatchManager()

    init {
        val vertexShader = core.resources.get("default").get<ShaderData>("sprite.vertex")
        val fragmentShader = core.resources.get("default").get<ShaderData>("sprite.fragment")
        spriteBatch.addRenderer(Sprite::class, SpriteBatch(Shader(vertexShader, fragmentShader)))
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        return spriteBatch.execute(drawableList, layer.camera.combined)
    }

    override fun dispose() {

    }
}