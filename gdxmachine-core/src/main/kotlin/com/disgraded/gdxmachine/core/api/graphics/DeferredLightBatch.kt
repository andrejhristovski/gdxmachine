package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.DeferredLightRenderer
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class DeferredLightBatch(private val projection: Projection) : DrawableBatch {

    private lateinit var lightList: ArrayList<Light>
    private lateinit var ambientColor: Color
    private val normalBatch = NormalBatch()
    private val standardBatch = StandardBatch()
    private val deferredLightRenderer = DeferredLightRenderer()

    fun apply(lightList: ArrayList<Light>, ambientColor: Color) {
        this.lightList = lightList
        this.ambientColor = ambientColor
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0
        val standardFrame = FrameBuffer(Pixmap.Format.RGBA8888, projection.getVirtualWidth().toInt(),
                projection.getVirtualHeight().toInt(), false)
        standardFrame.begin()
        gpuCalls += standardBatch.render(drawableList, projectionMatrix)
        standardFrame.end()
        val standardSnap = TextureRegion(standardFrame.colorBufferTexture)
        standardSnap.flip(false, true)


        val normalFrame = FrameBuffer(Pixmap.Format.RGBA8888, projection.getVirtualWidth().toInt(),
                projection.getVirtualHeight().toInt(), false)
        normalFrame.begin()
        gpuCalls += normalBatch.render(drawableList, projectionMatrix)
        normalFrame.end()
        val normalSnap = TextureRegion(normalFrame.colorBufferTexture)
        normalSnap.flip(false, true)


        projection.apply()

        gpuCalls += deferredLightRenderer.render(ambientColor, lightList, standardSnap, normalSnap, projectionMatrix)

        standardFrame.dispose()
        normalFrame.dispose()
        return gpuCalls
    }

    override fun dispose() {
        standardBatch.dispose()
        normalBatch.dispose()
        deferredLightRenderer.dispose()
    }
}