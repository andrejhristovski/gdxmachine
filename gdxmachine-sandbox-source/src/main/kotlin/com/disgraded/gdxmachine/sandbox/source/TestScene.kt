package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.disgraded.gdxmachine.core.api.graphics.drawable.*
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.resource.asset.PlainText
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private var background = arrayListOf<Sprite>()
    private lateinit var player: Sprite
    private var lights = arrayListOf<Light>()
    private lateinit var text: Text
    private lateinit var desc: Text

    override fun initialize() {
        context.graphics.createViewport()
        context.graphics.createViewport("hud")
        context.graphics.getViewport().enableLights()
        context.graphics.getViewport().ambientColor = Color.BLACK
        val wallTexture = context.resources.get<Texture>("initial", "wall")
        val wallNormalTexture = context.resources.get<Texture>("initial", "wall_normal")
        val playerTexture = context.resources.get<Texture>("initial", "player")
        val playerNormalTexture = context.resources.get<Texture>("initial", "player_normal")
        val textBitmap = context.resources.get<BitmapFont>("initial", "text")
        val description = context.resources.get<PlainText>("initial", "desc")

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

        val light = PointLight()
        light.intensity = 1f
        light.color = Color.WARM_WHITE
        light.x = 200f
        light.y = 200f
        lights.add(light)

        text = Text(textBitmap)
        text.x = -600f
        text.y = 320f
        text.anchorX = 0f

        desc = Text(textBitmap)
        desc.x = -600f
        desc.y = 220f
        desc.anchorX = 0f
        desc.displayText = description.text
    }

    override fun update(deltaTime: Float) {
        text.displayText = "FPS:${context.graphics.getFPS()} :: GPU CALLS:${context.graphics.getGPUCalls()}"
//        context.graphics.getViewport().camera.position.x += 30f * deltaTime

        for (background in this.background) {
            context.graphics.getViewport().draw(background)
        }
        context.graphics.getViewport().draw(player)
        for (light in lights) {
//            light.x += 100f * deltaTime
            context.graphics.getViewport().draw(light)
        }

        val hud = context.graphics.getViewport("hud")
        hud.draw(desc)
        hud.draw(text)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}