package com.disgraded.gdxmachine.core.api.graphics.batch

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.Projection
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.renderer.DeferredLightingRenderer
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.graphics.utils.FrameBuffer

class DeferredLightingBatch(private val projection: Projection) : DrawableBatch {

    private lateinit var lightList: ArrayList<Drawable>
    private lateinit var ambientColor: Color
    private val diffuseBatch = DiffuseBatch()
    private val bumpBatch = BumpBatch()
    private val lightAttenuationBatch = LightAttenuationBatch(projection)
    private val lightBumpBatch = LightBumpBatch(projection)

    private val diffuseBuffer = FrameBuffer(projection)
    private val bumpBuffer = FrameBuffer(projection)
    private val lightBumpBuffer = FrameBuffer(projection, Color(.5f, .5f, 1f, 1f))
    private val lightAttenuationBuffer = FrameBuffer(projection, Color(0f, 0f, 0f, 0f))

    private val lightSceneBatch = DeferredLightingRenderer()

    fun apply(lightList: ArrayList<Drawable>, ambientColor: Color) {
        this.lightList = lightList
        this.ambientColor = ambientColor
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0

        diffuseBuffer.use { gpuCalls += diffuseBatch.render(drawableList, projectionMatrix) + 1 }
        bumpBuffer.use { gpuCalls += bumpBatch.render(drawableList, projectionMatrix) + 1 }
        lightBumpBuffer.use { gpuCalls += lightBumpBatch.render(lightList, projectionMatrix) + 1 }
        lightAttenuationBuffer.use { gpuCalls += lightAttenuationBatch.render(lightList, projectionMatrix) + 1 }
        lightSceneBatch.render(projection.camera.position.x, projection.camera.position.y, diffuseBuffer.buffer,
                bumpBuffer.buffer, lightBumpBuffer.buffer, lightAttenuationBuffer.buffer, ambientColor, projectionMatrix)
        return gpuCalls + 1
    }

    override fun dispose() {
        diffuseBatch.dispose()
        lightBumpBatch.dispose()
        lightAttenuationBatch.dispose()
        bumpBatch.dispose()
    }
}