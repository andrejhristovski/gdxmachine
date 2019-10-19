package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.Api
import com.disgraded.gdxmachine.framework.graphics.Drawable
import com.disgraded.gdxmachine.framework.graphics.BatchManager
import com.disgraded.gdxmachine.framework.graphics.Layer
import com.disgraded.gdxmachine.framework.graphics.Renderer
import com.disgraded.gdxmachine.framework.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.resources.assets.ShaderData

class Renderer2D: Renderer {

    private val spriteBatch = BatchManager()

    init {
        val vertexShader = Api.resources.get("default").get<ShaderData>("sprite.vertex")
        val fragmentShader = Api.resources.get("default").get<ShaderData>("sprite.fragment")
        spriteBatch.addRenderer(Sprite::class, SpriteBatch(Shader(vertexShader, fragmentShader)))
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        return spriteBatch.execute(drawableList, layer.camera.combined)
    }

    override fun dispose() {

    }
}