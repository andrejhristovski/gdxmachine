package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.renderer.DeferredLightRenderer
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class DeferredLightBatch(private val projection: Projection) : DrawableBatch {

    private lateinit var lightList: ArrayList<Light>
    private lateinit var ambientColor: Color
    private val diffuseBatch = DiffuseBatch()
    private val bumpBatch = BumpBatch()

    fun apply(lightList: ArrayList<Light>, ambientColor: Color) {
        this.lightList = lightList
        this.ambientColor = ambientColor
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0
        gpuCalls += bumpBatch.render(drawableList, projectionMatrix)
        return gpuCalls
    }

    override fun dispose() {
        diffuseBatch.dispose()
        bumpBatch.dispose()
    }
}