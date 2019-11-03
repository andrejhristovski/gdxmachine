package com.disgraded.gdxmachine.sandbox.source.scenes

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.scenes.Scene

class FirstScene : Scene() {

    private lateinit var sprite: Sprite

    override fun initialize() {
        val assets = core.resources.get("_default")
        sprite = Sprite()
        sprite.setTexture(assets.get<Texture>("no_image"))
    }

    override fun update(deltaTime: Float) {
        mainLayer.draw(sprite)
    }

    override fun destroy() {

    }
}