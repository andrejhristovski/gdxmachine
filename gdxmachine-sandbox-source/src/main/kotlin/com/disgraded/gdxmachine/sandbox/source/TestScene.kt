package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private var background = arrayListOf<Sprite>()
    private lateinit var player: Sprite

    override fun initialize() {
        context.graphics.createViewport()
        val wallTexture = context.resources.get<Texture>("initial", "wall")
        val wallNormalTexture = context.resources.get<Texture>("initial", "wall_normal")
        val playerTexture = context.resources.get<Texture>("initial", "player")

        val startX = -800
        val startY = -500
        for (i in 0..10) {
            for(j in 0..10) {
                val background = Sprite(wallTexture)
                background.setNormalMap(wallNormalTexture)
                background.x = startX + wallTexture.width.toFloat() * j
                background.y = startY + wallTexture.height.toFloat() * i
                this.background.add(background)
            }
        }

        player = Sprite(playerTexture)
    }

    override fun update(deltaTime: Float) {
        context.graphics.getViewport().draw(player)
        for (background in this.background) {
            context.graphics.getViewport().draw(background)
        }
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}