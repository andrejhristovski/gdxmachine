package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.drawables.Text

class TextBatch : Batch {

    private val textBatch = SpriteBatch(1)
    private lateinit var projectionMatrix: Matrix4
    private var active = false

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun draw(drawable: Drawable) {
        if (!active) {
            active = true
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
            textBatch.begin()
        }

        val text = drawable as Text

        val localProjection = Matrix4()
        localProjection.translate(-text.x, -text.y, 0f)
        localProjection.rotate(0f, 0f, 1f, text.angle)
        localProjection.translate(text.x, text.y, 0f)

        textBatch.projectionMatrix = Matrix4(projectionMatrix).mul(localProjection)

//        val x = text.x - text.getGlyph().width * text.anchorX
//        val y = text.y + text.getGlyph().height * text.anchorY
        text.getBitmapFont().draw(textBatch, text.getGlyph(), 0f, 0f)

//        text.getGlyph().

    }

    override fun end(): Int {
        if (active) {
            active = false
            textBatch.end()
        }
        return textBatch.renderCalls
    }

    override fun dispose() = textBatch.dispose()
}