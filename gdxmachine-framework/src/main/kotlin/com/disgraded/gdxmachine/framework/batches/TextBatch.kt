package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color as GdxColor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.drawables.Text

class TextBatch : Batch {

    private val textBatch = SpriteBatch(8191)
    private lateinit var projectionMatrix: Matrix4
    private var textMatrix = Matrix4()
    private var color = GdxColor(1f, 1f, 1f, 0f)

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun draw(drawable: Drawable) {
        if (!textBatch.isDrawing) {
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
            textBatch.begin()
        }

        val text = drawable as Text

        textMatrix.idt()
        val additionalX = text.glyph.width * text.anchorX * text.scaleX
        val additionalY = text.glyph.height * text.anchorY * text.scaleY

        textMatrix.setToTranslation(text.x, text.y, 0f)
        textMatrix.rotate(0f, 0f, 1f, text.angle)
        textMatrix.translate(-additionalX, additionalY, 0f)
        textMatrix.scale(text.scaleX, text.scaleY, 1f)
        textBatch.projectionMatrix = textMatrix.mulLeft(projectionMatrix)
        text.font.draw(textBatch, text.glyph, 0f, 0f)
    }

    override fun end(): Int {
        if (textBatch.isDrawing) {
            textBatch.end()
        }
        return textBatch.renderCalls
    }

    override fun dispose() = textBatch.dispose()
}