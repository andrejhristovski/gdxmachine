package com.disgraded.gdxmachine.sandbox.source.scene

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.*
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene

class FirstScene : Scene {

    private lateinit var renderContext: RenderContext.Api
    private lateinit var sprite: Sprite

    override fun initialize(context: Context) {
        renderContext = context.graphics.getContext()
        sprite = Sprite()
        sprite.textureRegion = TextureRegion(context.resources.get<Texture>("initial", "player"))
        sprite.colorLeftTop = Color.RED
        sprite.colorLeftBottom = Color.AMBER
        sprite.colorRightTop = Color.CYAN
        sprite.colorRightBottom = Color.LIME
    }

    override fun update(deltaTime: Float) {
        renderContext.draw(sprite)
    }

    override fun destroy() {

    }
}