package com.disgraded.gdxmachine.sandbox.source.scenes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.drawables.Text
import com.disgraded.gdxmachine.framework.scenes.Scene

class FirstScene : Scene() {

    private lateinit var sprite: Sprite
    private lateinit var text: Text

    override fun initialize() {
        val assets = core.resources.get("_default")
        sprite = Sprite()
        sprite.setTexture(assets.get<Texture>("no_image"))

        text = Text(assets.get("mono"))
    }

    override fun update(deltaTime: Float) {
        mainLayer.draw(sprite)
        mainLayer.draw(text)
        text.angle += 100f * deltaTime
        text.x += 100f * deltaTime

    }

    override fun destroy() {

    }
}