package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Text

class TextDiffuseRenderer : SpriteBatch(8191), Renderer {

    private val shaderFactory = ShaderFactory.getInstance()

    override var active: Boolean = false

    init {
//        this.shader = shaderFactory.get("text_diffuse", "text_diffuse")
    }

    override fun start() {
        this.begin()
        active = true
    }

    override fun draw(drawable: Drawable) {
        val text = drawable as Text
        this.packedColor = text.color.toFloatBits()
        text.bitmapFont.draw(this, text.content, text.x, text.y, 0, 9, 0f, 1, true)
    }

    override fun finish(): Int {
        this.end()
        active = false
        return this.renderCalls
    }
}