package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import kotlin.reflect.KClass

class BatchManager: Disposable {

    private val batchMap = hashMapOf<KClass<out Drawable>, Batch>()
    private var currentBatch: Batch? = null

    fun addBatch(drawableType: KClass<out Drawable>, batch: Batch) {
        if (batchMap.containsKey(drawableType)) throw RuntimeException("batch for $drawableType already exist!")
        batchMap[drawableType] = batch
    }

    fun execute(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0
        for ((_, batch) in batchMap) {
            batch.setProjectionMatrix(projectionMatrix)
        }

        for (drawable in drawableList) {
            val batch = batchMap[drawable::class] ?: continue
            if (batch != currentBatch) {
                if (currentBatch != null) gpuCalls += currentBatch!!.end()
                currentBatch = batch
            }
            currentBatch!!.draw(drawable)
        }
        if (currentBatch != null) gpuCalls += currentBatch!!.end()
        return gpuCalls
    }

    override fun dispose() {
        for ((_, batch) in batchMap) {
            batch.dispose()
        }
    }
}