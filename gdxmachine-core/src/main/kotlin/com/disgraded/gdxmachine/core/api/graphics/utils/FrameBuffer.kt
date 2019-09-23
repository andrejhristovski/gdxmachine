package com.disgraded.gdxmachine.core.api.graphics.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.Projection
import com.badlogic.gdx.graphics.glutils.FrameBuffer as GdxFrameBuffer

class FrameBuffer(private val projection: Projection, private var color: Color = Color(0f, 0f, 0f, 0f)): Disposable {

    private var virtualWidth = 0f
    private var virtualHeight = 0f

    private var fb: GdxFrameBuffer? = null
    lateinit var buffer : TextureRegion

    override fun dispose() {
        if (fb != null) fb!!.dispose()
    }

    fun use(action: (FrameBuffer) -> Unit) {
        adapt(projection.getVirtualWidth(), projection.getVirtualHeight())
        fb!!.begin()
        Gdx.gl.glClearColor(color.r, color.g ,color.b, color.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        action(this)
        fb!!.end()
        buffer = TextureRegion(fb!!.colorBufferTexture)
        buffer.flip(false, true)
        projection.apply()
    }

    private fun adapt(virtualWidth: Float, virtualHeight: Float) {
        if (virtualWidth != this.virtualWidth || virtualHeight != this.virtualHeight) {
            if (fb != null) fb!!.dispose()
            fb = GdxFrameBuffer(Pixmap.Format.RGBA8888, virtualWidth.toInt(), virtualHeight.toInt(), false)
            this.virtualWidth = virtualWidth
            this.virtualHeight = virtualHeight
        }
    }

}