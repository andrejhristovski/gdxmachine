package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.disgraded.gdxmachine.core.api.graphics.drawable.*
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private var background = arrayListOf<Sprite>()
    private lateinit var player: Sprite
    private lateinit var light: Light
    private lateinit var light2: Light
    private lateinit var text: Text

    override fun initialize() {
        context.graphics.createViewport()
        context.graphics.createViewport("hud")
        context.graphics.getViewport().enableLights()
//        context.graphics.getViewport().ambientColor = Color.WARM_WHITE
//        context.graphics.getViewport().project(.5f, 0f, .5f, 1f)
        val wallTexture = context.resources.get<Texture>("initial", "wall")
        val wallNormalTexture = context.resources.get<Texture>("initial", "wall_normal")
        val playerTexture = context.resources.get<Texture>("initial", "player")
        val textBitmap = context.resources.get<BitmapFont>("initial", "text")

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

        player = Sprite(playerTexture)
        player.x = -100f
        player.y = -100f
        player.setScale(.5f)

        light = PointLight(300f, 300f)
        light.setColor(Color.WARM_WHITE)

        light2 = PointLight(100f, 10f)
        light2.setColor(Color.RED)

        text = Text(textBitmap)
        text.x = -600f
        text.y = 320f
        text.anchorX = 0f
    }

    override fun update(deltaTime: Float) {
        text.displayText = "FPS: ${context.graphics.getFPS()} :: GPU CALLS: ${context.graphics.getGPUCalls()}"
        light.x += 10f * deltaTime

        for (background in this.background) {
            context.graphics.getViewport().draw(background)
        }
        context.graphics.getViewport().draw(player)
        context.graphics.getViewport().draw(light)
        context.graphics.getViewport().draw(light2)
        context.graphics.getViewport("hud").draw(text)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}