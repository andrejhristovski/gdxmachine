package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private var background = arrayListOf<Sprite>()
    private lateinit var player: Sprite

    override fun initialize() {
        context.graphics.createViewport()
//        context.graphics.getViewport().project(.5f, 0f, .5f, 1f)
//        context.graphics.getViewport().enableLights(true)
        val rockTexture = context.resources.get<Texture>("initial", "rock")
        val rockNormalTexture = context.resources.get<Texture>("initial", "rock_normal")
        val playerTexture = context.resources.get<Texture>("initial", "player")

        val startX = -800
        val startY = -500
        for (i in 0..10) {
            for(j in 0..10) {
                val background = Sprite(rockTexture)
//                background.setNormalMap(rockNormalTexture)
                background.x = startX + rockTexture.width.toFloat() * j
                background.y = startY + rockTexture.height.toFloat() * i
                this.background.add(background)
            }
        }

        player = Sprite(playerTexture)
    }

    override fun update(deltaTime: Float) {

        for (background in this.background) {
            context.graphics.getViewport().draw(background)
        }
        context.graphics.getViewport().draw(player)
        println(context.graphics.getFPS())
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}