package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.disgraded.gdxmachine.core.api.graphics.drawable.*
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private var background = arrayListOf<Sprite>()
    private lateinit var player: Sprite
    private var lights = arrayListOf<Light>()
    private lateinit var text: Text

    override fun initialize() {
        context.graphics.createViewport()
        context.graphics.createViewport("hud")
        context.graphics.getViewport().enableLights()
        context.graphics.getViewport().project(.5f, 0f, .5f, 1f)
//        context.graphics.getViewport().ambientColor = Color.BLUE_GREY
        val wallTexture = context.resources.get<Texture>("initial", "wall")
        val wallNormalTexture = context.resources.get<Texture>("initial", "wall_normal")
        val playerTexture = context.resources.get<Texture>("initial", "player")
        val playerNormalTexture = context.resources.get<Texture>("initial", "player_normal")
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
        player.setNormalMap(playerNormalTexture)
        player.x = -100f
        player.y = -100f
        player.setScale(.5f)

        var light = PointLight()
        light.intensity = 1f
        light.color = Color.WARM_WHITE
        light.x = 200f
        light.y = -200f
        lights.add(light)

        light = PointLight()
        light.intensity = .5f
        light.x = -200f
        light.y = -300f
        light.color = Color.GREEN
        lights.add(light)


        text = Text(textBitmap)
        text.x = -600f
        text.y = 320f
        text.anchorX = 0f
    }

    override fun update(deltaTime: Float) {
        text.displayText = "FPS:${context.graphics.getFPS()} :: GPU CALLS:${context.graphics.getGPUCalls()}"

        for (background in this.background) {
            context.graphics.getViewport().draw(background)
        }
        context.graphics.getViewport().draw(player)
        for (light in lights) {
//            light.x += 100f * deltaTime
            context.graphics.getViewport().draw(light)
        }
        context.graphics.getViewport("hud").draw(text)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}