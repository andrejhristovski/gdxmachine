package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.drawables.Text

class TextBatch : Batch {

    private val textBatch = SpriteBatch(1)
    private lateinit var projectionMatrix: Matrix4
    private var textMatrix = Matrix4()

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
        val additionalX = text.getGlyph().width * text.anchorX * text.scaleX
        val additionalY = text.getGlyph().height * text.anchorY * text.scaleY

        textMatrix.setToTranslation(text.x, text.y, 0f)
        textMatrix.rotate(0f, 0f, 1f, text.angle)
        textMatrix.translate(-additionalX, additionalY, 0f)
        textMatrix.scale(text.scaleX, text.scaleY, 1f)
        textBatch.projectionMatrix = textMatrix.mulLeft(projectionMatrix)

        text.getBitmapFont().draw(textBatch, text.getGlyph(), 0f, 0f)
    }

    override fun end(): Int {
        if (textBatch.isDrawing) {
            textBatch.end()
        }
        return textBatch.renderCalls
    }

    override fun dispose() = textBatch.dispose()
}