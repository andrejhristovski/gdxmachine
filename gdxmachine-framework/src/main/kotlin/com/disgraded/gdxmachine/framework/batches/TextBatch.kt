package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.drawables.Text

class TextBatch(private val mode: Mode = Mode.DIFFUSE) : Batch {

    enum class Mode {
        DIFFUSE, BUMP
    }

    private val textBatch = SpriteBatch(8191)
    private lateinit var projectionMatrix: Matrix4
    private var textMatrix = Matrix4()
    private val defaultShader: Shader
    private var shader: Shader
    private var gpuCalls = 0

    private val forceShader: Boolean

    init {
        when(mode) {
            Mode.DIFFUSE -> {
                forceShader = false
                defaultShader = Core.graphics.getShader("text.tint")
            }

            Mode.BUMP -> {
                forceShader = true
                defaultShader = Core.graphics.getShader("text.bump")
            }
        }
        shader = defaultShader
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun draw(drawable: Drawable) {
        checkShader(drawable.shader)
        if (!textBatch.isDrawing) {
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
            textBatch.shader = shader
            textBatch.begin()
        }

        val text = drawable as Text

        textMatrix.idt()
        val additionalX = text.glyph.width * text.absolute.anchorX * text.absolute.scaleX
        val additionalY = text.glyph.height * text.absolute.anchorY * text.absolute.scaleY

        textMatrix.setToTranslation(text.absolute.x, text.absolute.y, 0f)
        textMatrix.rotate(0f, 0f, 1f, text.absolute.angle)
        textMatrix.translate(-additionalX, additionalY, 0f)
        textMatrix.scale(text.absolute.scaleX, text.absolute.scaleY, 1f)
        textBatch.projectionMatrix = textMatrix.mulLeft(projectionMatrix)
        try {
            text.font!!.draw(textBatch, text.glyph, 0f, 0f)
        }catch (e: Exception) {
            throw RuntimeException("Text can not be drawn without font")
        }
    }

    override fun end(): Int {
        if (textBatch.isDrawing) {
            flush()
        }
        val totalGpuCalls = gpuCalls
        gpuCalls = 0
        return  totalGpuCalls
    }

    override fun dispose() = textBatch.dispose()

    private fun checkShader(shader: Shader?) {
        if (forceShader) return
        if (shader == null) {
            if (this.shader !== defaultShader) {
                flush()
                this.shader = defaultShader
            }
        } else {
            flush()
            this.shader = shader
        }
    }

    private fun flush() {
        if (textBatch.isDrawing) {
            textBatch.end()
            gpuCalls += textBatch.renderCalls
        }
    }
}