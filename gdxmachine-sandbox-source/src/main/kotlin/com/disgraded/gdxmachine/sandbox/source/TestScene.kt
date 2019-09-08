package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.scene.Scene


class TestScene : Scene() {

    private lateinit var sprite : Sprite
    private lateinit var player: Sprite

    override fun initialize() {
        context.graphics.createViewport()
        sprite = Sprite(context.resources.get<Texture>("initial", "background"))
        sprite.setColor(Drawable2D.Corner.TOP_RIGHT, Color.DEEP_ORANGE)
        player = Sprite(context.resources.get<Texture>("initial", "player"))

    }

    override fun update(deltaTime: Float) {
        context.graphics.getViewport().draw(sprite)
//        context.graphics.getViewport("second").draw(sprite)

        context.graphics.getViewport().draw(player)

//        context.graphics.getViewport("second").draw(player)

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun destroy() {

    }
}