package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import kotlin.reflect.KClass

class BatchManager: Disposable {

    private val batchMap = hashMapOf<KClass<out Drawable>, Batch>()
    private var currentBatch: Batch? = null

    fun addRenderer(drawableType: KClass<out Drawable>, batch: Batch) {
        if (batchMap.containsKey(drawableType)) throw RuntimeException("") // TODO: message
        batchMap[drawableType] = batch
    }

    fun execute(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0
        for ((_, batch) in batchMap) {
            batch.setProjectionMatrix(projectionMatrix)
        }

        if (currentBatch != null) currentBatch!!.begin()
        for (drawable in drawableList) {
            val batch = batchMap[drawable::class] ?: continue
            if (batch != currentBatch) {
                if (currentBatch != null) gpuCalls += currentBatch!!.end()
                currentBatch = batch
                currentBatch!!.begin()
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