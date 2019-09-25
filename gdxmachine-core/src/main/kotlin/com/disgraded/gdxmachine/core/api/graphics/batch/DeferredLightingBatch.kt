package com.disgraded.gdxmachine.core.api.graphics.batch

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.Projection
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.renderer.DeferredLightingRenderer
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.graphics.utils.FrameBuffer

class DeferredLightingBatch(private val projection: Projection) : DrawableBatch {

    private lateinit var lightList: ArrayList<Light>
    private lateinit var ambientLight: Color
    private val diffuseBatch = DiffuseBatch()
    private val bumpBatch = BumpBatch()

    private val diffuseBuffer = FrameBuffer(projection)
    private val bumpBuffer = FrameBuffer(projection)

    private val lightSceneBatch = DeferredLightingRenderer(projection)

    fun apply(lightList: ArrayList<Light>, ambientLight: Color) {
        this.lightList = lightList
        this.ambientLight = ambientLight
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0

        diffuseBuffer.use { gpuCalls += diffuseBatch.render(drawableList, projectionMatrix) + 1 }
        bumpBuffer.use { gpuCalls += bumpBatch.render(drawableList, projectionMatrix) + 1 }
        lightSceneBatch.render(diffuseBuffer.buffer, bumpBuffer.buffer, ambientLight, lightList, projectionMatrix)
        return gpuCalls + 1
    }

    override fun dispose() {
        diffuseBatch.dispose()
        bumpBatch.dispose()
        diffuseBuffer.dispose()
        bumpBuffer.dispose()
    }
}