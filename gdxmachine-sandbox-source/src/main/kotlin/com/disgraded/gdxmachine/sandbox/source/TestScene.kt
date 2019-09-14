package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private var background = arrayListOf<Sprite>()
    private lateinit var player: Sprite
    private lateinit var light: Light

    override fun initialize() {
        context.graphics.createViewport()
//        context.graphics.getViewport().enableLights()
//        context.graphics.getViewport().ambientColor = Color.WARM_WHITE
//        context.graphics.getViewport().project(.5f, 0f, .5f, 1f)
        val wallTexture = context.resources.get<Texture>("initial", "wall")
        val wallNormalTexture = context.resources.get<Texture>("initial", "wall_normal")
        val playerTexture = context.resources.get<Texture>("initial", "player")

        val startX = -800
        val startY = -500
        for (i in 0..4) {
            for(j in 0..5) {
                val background = Sprite(wallTexture)
                background.setNormalMap(wallNormalTexture)
//                background.filter = Sprite.Filter.TIT
                background.x = startX + wallTexture.width.toFloat() * j
                background.y = startY + wallTexture.height.toFloat() * i
                this.background.add(background)
            }
        }

        player = Sprite(wallNormalTexture)
//        player.setScale(.5f)
//        player.setMask(playerTexture)
        player.filter = Sprite.Filter.TINT
        player.setColor(Color.BLUE)
        player.setColor(Drawable.Corner.TOP_RIGHT, Color.BROWN)
        player.x = -100f
        player.y = -100f
        player.setAnchor(.5f)

        light = Light()
        light.color = Color.CYAN
        light.x = .2f
        light.y = .2f
        light.z = .075f
    }

    override fun update(deltaTime: Float) {
        for (background in this.background) {
            context.graphics.getViewport().draw(background)
        }
        context.graphics.getViewport().draw(player)
        context.graphics.getViewport().draw(light)

        println(context.graphics.getFPS())
//        player.x += 40f * deltaTime
        player.rotation += 100f * deltaTime
//        light.z += .1f * deltaTime
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}