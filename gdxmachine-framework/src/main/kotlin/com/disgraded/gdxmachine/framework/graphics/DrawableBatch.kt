package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable
import kotlin.reflect.KClass

class DrawableBatch: Disposable {

    private val drawableHandlerMap = hashMapOf<KClass<out Drawable>, DrawableHandler>()
    private var currentDrawableHandler: DrawableHandler? = null

    fun addRenderer(drawableType: KClass<out Drawable>, drawableHandler: DrawableHandler) {
        if (drawableHandlerMap.containsKey(drawableType)) throw RuntimeException("") // TODO: message
        drawableHandlerMap[drawableType] = drawableHandler
    }

    fun execute(drawableList: ArrayList<Drawable>): Int {
        var gpuCalls = 0
        for (drawable in drawableList) {
            val drawableRenderer = drawableHandlerMap[drawable::class] ?: continue
            if (drawableRenderer != currentDrawableHandler) {
                if (currentDrawableHandler != null) gpuCalls += currentDrawableHandler!!.end()
                currentDrawableHandler = drawableRenderer
                currentDrawableHandler!!.begin()
            }
            currentDrawableHandler!!.draw(drawable)
        }
        if (currentDrawableHandler != null) gpuCalls += currentDrawableHandler!!.end()
        return gpuCalls
    }

    override fun dispose() {
        for ((_, renderer) in drawableHandlerMap) {
            renderer.dispose()
        }
    }
}