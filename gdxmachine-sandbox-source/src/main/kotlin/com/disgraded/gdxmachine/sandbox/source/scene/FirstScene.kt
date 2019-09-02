package com.disgraded.gdxmachine.sandbox.source.scene

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.RenderContext
import com.disgraded.gdxmachine.core.api.graphics.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene

class FirstScene : Scene {

    private lateinit var context: Context
    private lateinit var renderContext: RenderContext.Api
    private lateinit var sprite: Sprite

    override fun initialize(context: Context) {
        println("test 1")
        this.context = context

        renderContext = context.graphics.getContext()
        sprite = Sprite()
        val texture = context.resources.get<Texture>("initial", "bg")
        sprite.setTexture(texture)
    }

    override fun update(deltaTime: Float) {
        renderContext.draw(sprite)
    }

    override fun destroy() {
        println("end test 1")
    }
}