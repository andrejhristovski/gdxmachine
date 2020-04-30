package com.disgraded.gdxmachine.framework.renderers.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.disgraded.gdxmachine.framework.core.graphics.GraphicsModule
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Sprite

class LayerBuffer(private val format: Pixmap.Format, private var hasDepth: Boolean): Disposable {

    private var frameBuffer = FrameBuffer(format, 1, 1, hasDepth)
    private val texture = TextureRegion()

    val color = Color.RED.copy()

    fun draw(layer: Layer, action:() -> Unit) {
        update(layer)
        frameBuffer.begin()
        layer.applyProjection(true)
        Gdx.gl.glClearColor(color.r, color.g ,color.b, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        action()
        frameBuffer.end()
        layer.applyProjection(true)
        texture.setRegion(frameBuffer.colorBufferTexture)
        texture.flip(false, true)
    }

    private fun update(layer: Layer) {
        val widthCheck = layer.worldWidth.toInt() == frameBuffer.width
        val heightCheck = layer.worldHeight.toInt() == frameBuffer.height
        if (!widthCheck || !heightCheck) {
            frameBuffer.dispose()
            frameBuffer = FrameBuffer(format, layer.worldWidth.toInt(), layer.worldHeight.toInt(), hasDepth)
        }
    }

    fun getTexture(): TextureRegion = texture

    override fun dispose() {
        frameBuffer.dispose()
    }
}