package com.disgraded.gdxmachine.core.api.graphics.batch

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.Projection
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.graphics.utils.FrameBuffer

class DeferredLightBatch(private val projection: Projection) : DrawableBatch {

    private lateinit var lightList: ArrayList<Drawable>
    private lateinit var ambientColor: Color
    private val diffuseBatch = DiffuseBatch()
    private val bumpBatch = BumpBatch()
    private val lightAttenuationBatch = LightAttenuationBatch(projection)
    private val lightDiffuseBatch = LightDiffuseBatch(projection)

    private val diffuseBuffer = FrameBuffer(projection)
    private val bumpBuffer = FrameBuffer(projection)
    private val lightDiffuseBuffer = FrameBuffer(projection)
    private val lightAttenuationBuffer = FrameBuffer(projection)

    private val lightSceneBatch = LightSceneBatch()

    fun apply(lightList: ArrayList<Drawable>, ambientColor: Color) {
        this.lightList = lightList
        this.ambientColor = ambientColor
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0

        diffuseBuffer.use { gpuCalls += diffuseBatch.render(drawableList, projectionMatrix) + 1 }
        bumpBuffer.use { gpuCalls += bumpBatch.render(drawableList, projectionMatrix) + 1 }
        lightDiffuseBuffer.use { gpuCalls += lightDiffuseBatch.render(lightList, projectionMatrix) + 1 }
        lightAttenuationBuffer.use { gpuCalls += lightAttenuationBatch.render(lightList, projectionMatrix) + 1 }
        lightSceneBatch.render(projection.camera.position.x, projection.camera.position.y, diffuseBuffer.buffer,
                bumpBuffer.buffer, lightDiffuseBuffer.buffer, lightAttenuationBuffer.buffer, ambientColor, projectionMatrix)
        return gpuCalls + 1
    }

    override fun dispose() {
        diffuseBatch.dispose()
        lightDiffuseBatch.dispose()
        lightAttenuationBatch.dispose()
        bumpBatch.dispose()
    }
}