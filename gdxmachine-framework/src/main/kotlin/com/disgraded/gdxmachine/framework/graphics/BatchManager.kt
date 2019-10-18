package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable
import kotlin.reflect.KClass

class BatchManager: Disposable {

    private val drawableHandlerMap = hashMapOf<KClass<out Drawable>, Batch>()
    private var currentBatch: Batch? = null

    fun addRenderer(drawableType: KClass<out Drawable>, batch: Batch) {
        if (drawableHandlerMap.containsKey(drawableType)) throw RuntimeException("") // TODO: message
        drawableHandlerMap[drawableType] = batch
    }

    fun execute(drawableList: ArrayList<Drawable>): Int {
        var gpuCalls = 0
        for (drawable in drawableList) {
            val drawableRenderer = drawableHandlerMap[drawable::class] ?: continue
            if (drawableRenderer != currentBatch) {
                if (currentBatch != null) gpuCalls += currentBatch!!.end()
                currentBatch = drawableRenderer
                currentBatch!!.begin()
            }
            currentBatch!!.draw(drawable)
        }
        if (currentBatch != null) gpuCalls += currentBatch!!.end()
        return gpuCalls
    }

    override fun dispose() {
        for ((_, renderer) in drawableHandlerMap) {
            renderer.dispose()
        }
    }
}