package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.MaskedSprite
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private lateinit var background : MaskedSprite

    override fun initialize() {
        context.graphics.createViewport()
        val playerTexture = context.resources.get<Texture>("initial", "player")
        val bgTexture = context.resources.get<Texture>("initial", "background")

        background = MaskedSprite(bgTexture, playerTexture)
        background.setColor(Drawable2D.Corner.TOP_RIGHT, Color.DEEP_ORANGE)
    }

    override fun update(deltaTime: Float) {
        for (i in 1..10) {
            context.graphics.getViewport().draw(background)
        }
        println(context.graphics.glCalls)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}