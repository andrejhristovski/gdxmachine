package com.disgraded.gdxmachine.framework.renderers.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.framework.core.graphics.GraphicsModule
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Sprite

class LayerBuffer(private val format: Pixmap.Format, private var hasDepth: Boolean): Disposable {

    private var frameBuffer = FrameBuffer(format, 1, 1, hasDepth)
    private val sprite = Sprite()
    private val texture = TextureRegion()

    val color = Color.BLACK.copy()

    init {
        sprite.flipY = true
    }

    fun draw(layer: Layer, action:() -> Unit) {
        update(layer)
        frameBuffer.begin()
        Gdx.gl.glClearColor(color.r, color.g ,color.b, 1f)
        Gdx.gl.glClear(GraphicsModule.getMask())
        action()
        frameBuffer.end()
        layer.apply()
    }

    private fun update(layer: Layer) {
        val widthCheck = layer.screenWidth == frameBuffer.width
        val heightCheck = layer.screenHeight == frameBuffer.height
        if (!widthCheck || !heightCheck) {
            frameBuffer.dispose()
            frameBuffer = FrameBuffer(format, layer.worldWidth.toInt(), layer.worldHeight.toInt(), hasDepth)
        }
        texture.setRegion(frameBuffer.colorBufferTexture)
        sprite.setTexture(texture)
    }

    fun getSprite(): Sprite = sprite

    override fun dispose() {

    }
}