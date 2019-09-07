package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private lateinit var sprite : Sprite

    override fun initialize() {
        context.graphics.createViewport()
        sprite = Sprite(context.resources.get<Texture>("initial", "background"))
        sprite.setColor(Drawable2D.Corner.TOP_RIGHT, Color.DEEP_ORANGE)
    }

    override fun update(deltaTime: Float) {
        context.graphics.getViewport().draw(sprite)
    }

    override fun destroy() {

    }
}