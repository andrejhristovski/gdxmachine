package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Text

class TextDiffuseRenderer : SpriteBatch(8191), Renderer {

    private val shaderFactory = ShaderFactory.getInstance()

    private val shaderVertexPrefix = "text_diffuse"
    private val shaderFragmentPrefix = "text_diffuse"

    override var active: Boolean = false

    init {
        shader = shaderFactory.get(shaderVertexPrefix, shaderFragmentPrefix)
    }

    override fun start() {
        this.begin()
        active = true
    }

    override fun draw(drawable: Drawable) {
        val text = drawable as Text
        text.bitmapFont.draw(this, text.glyphLayout, text.x - (text.glyphLayout.width * text.anchorX),
                text.y - (text.glyphLayout.height * text.anchorY))
    }

    override fun finish(): Int {
        this.end()
        active = false
        return this.renderCalls
    }
}