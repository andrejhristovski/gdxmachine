package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.graphics.RenderContext
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene

class TestScene : Scene {

    private lateinit var sprite: Sprite
    private lateinit var sprite2: Sprite
    private lateinit var render: RenderContext.Api
    private lateinit var context: Context

    override fun initialize(context: Context) {
        this.context = context
        render = context.graphics.getContext()
        sprite = Sprite()
        val texture = context.resources.get<Texture>("initial", "rock")
        sprite.textureRegion = TextureRegion(texture)
        sprite.setColor(Drawable2D.Corner.TOP_LEFT, Color.RED)
        sprite.scaleX = .5f
        sprite.scaleY = .5f
        sprite.z = 0f

        sprite2 = Sprite()
        sprite2.textureRegion = TextureRegion(texture)
        sprite2.z = -1f
    }

    override fun update(deltaTime: Float) {
        render.draw(sprite2)
        render.draw(sprite)
        println("FPS: ${context.graphics.getFPS()}")

    }

    override fun destroy() {

    }
}