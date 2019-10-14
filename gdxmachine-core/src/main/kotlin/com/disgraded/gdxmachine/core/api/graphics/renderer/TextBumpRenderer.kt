package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Text

class TextBumpRenderer : SpriteBatch(8191), Renderer {

    private val shaderFactory = ShaderFactory.getInstance()

    private val shaderVertexPrefix = "text_bump"
    private val shaderFragmentPrefix = "text_bump"

    override var active: Boolean = false

    init {
        shader = shaderFactory.get(shaderVertexPrefix, shaderFragmentPrefix)
    }

    override fun start() {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
        this.begin()
        active = true
    }

    override fun draw(drawable: Drawable) {
        val text = drawable as Text
        val oldProjectionMatrix = projectionMatrix.cpy()
        val projection = projectionMatrix
        projection.translate(text.x, text.y, 0f)
        projection.scale(text.scaleX, text.scaleY, 0f)
        projection.rotate(Vector3(0f, 0f, 1f), text.angle)
        projectionMatrix = projection
        text.getBitmapFont().draw(this, text.getGlyph(), 0 - (text.getGlyph().width * text.anchorX),
                0 + (text.getGlyph().height * text.anchorY))
        projectionMatrix = oldProjectionMatrix
    }

    override fun finish(): Int {
        this.end()
        active = false
        return this.renderCalls
    }
}