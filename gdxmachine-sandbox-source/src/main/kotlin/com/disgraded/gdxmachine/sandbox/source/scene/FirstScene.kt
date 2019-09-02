package com.disgraded.gdxmachine.sandbox.source.scene

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.Color
import com.disgraded.gdxmachine.core.api.graphics.Drawable
import com.disgraded.gdxmachine.core.api.graphics.RenderContext
import com.disgraded.gdxmachine.core.api.graphics.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene

class FirstScene : Scene {

    private lateinit var context: Context
    private lateinit var renderContext: RenderContext.Api
    private lateinit var sprite: Sprite
    private lateinit var sprite2: Sprite

    override fun initialize(context: Context) {
        println("test 1")
        this.context = context

        renderContext = context.graphics.getContext()
        sprite = Sprite()
        val texture = context.resources.get<Texture>("initial", "player")
        sprite.setTexture(texture)
        sprite.effect = Drawable.Effect.GREYSCALE_COLORED
        sprite.color = Color("#7825b3", 1f)
        sprite.intensity = 1f

        sprite2 = Sprite()
        val texture2 = context.resources.get<Texture>("initial", "bg")
        sprite2.setTexture(texture2)
        sprite2.effect = Drawable.Effect.TINT
    }

    override fun update(deltaTime: Float) {
        renderContext.draw(sprite2)
        renderContext.draw(sprite)
    }

    override fun destroy() {
        println("end test 1")
    }
}