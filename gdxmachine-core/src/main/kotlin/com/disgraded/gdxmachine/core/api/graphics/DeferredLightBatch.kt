package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class DeferredLightBatch(private val projection: Projection) : DrawableBatch {

    private lateinit var lightList: ArrayList<Drawable>
    private lateinit var ambientColor: Color
    private val diffuseBatch = DiffuseBatch()
    private val attenuationBatch = AttenuationBatch(projection)
    private val bumpBatch = BumpBatch()

    fun apply(lightList: ArrayList<Drawable>, ambientColor: Color) {
        this.lightList = lightList
        this.ambientColor = ambientColor
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0
        gpuCalls += diffuseBatch.render(drawableList, projectionMatrix)
        gpuCalls += attenuationBatch.render(lightList, projectionMatrix)
        return gpuCalls
    }

    override fun dispose() {
        diffuseBatch.dispose()
        bumpBatch.dispose()
    }
}