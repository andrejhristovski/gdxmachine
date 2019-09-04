package com.disgraded.gdxmachine.sandbox.source.scene

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.*
import com.disgraded.gdxmachine.core.api.scene.Scene

class FirstScene : Scene {

    private lateinit var context: Context
    private lateinit var renderContext: RenderContext.Api
    private val spriteList = arrayListOf<Drawable>()

    override fun initialize(context: Context) {
        println("test 1")
        this.context = context

        renderContext = context.graphics.getContext()
        for (i in 1..100) {
            val sprite = MaskedSprite()
            val texture1 = context.resources.get<Texture>("initial", "bg")
            val texture2 = context.resources.get<Texture>("initial", "player")
            sprite.setTexture(texture1)
            if (i % 2 == 0) sprite.setMask(texture2)
            else sprite.setMask(texture1)
            sprite.effect = Drawable.Effect.SEPIA
            sprite.color = Color("#7825b3", 1f)
            sprite.intensity = .5f
            spriteList.add(sprite)
        }

//        for (i in 1..100) {
//            val sprite = Sprite()
//            val texture = context.resources.get<Texture>("initial", "bg")
//            sprite.setTexture(texture)
//            sprite.effect = Drawable.Effect.SEPIA
//            sprite.color = Color("#7825b3", 1f)
//            sprite.intensity = .5f
//            spriteList.add(sprite)
//        }
    }

    override fun update(deltaTime: Float) {
        for (sprite in spriteList) {
            renderContext.draw(sprite)
        }
        println(context.graphics.getFPS())
    }

    override fun destroy() {
        println("end test 1")
    }
}