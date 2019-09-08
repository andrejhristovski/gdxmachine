package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.renderer.MaskedSpriteRenderer
import com.disgraded.gdxmachine.core.api.graphics.renderer.Renderer2D
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteRenderer

class DrawableBatch(private val projection: Projection): Disposable {

    private val rendererMap = hashMapOf<String, Renderer2D>()
    private var currentRenderer: Renderer2D
    private val drawableList = arrayListOf<Drawable>()

    private var gpuCalls = 0

    init {
        rendererMap["sprite"] = SpriteRenderer()
        rendererMap["masked_sprite"] = MaskedSpriteRenderer()

        currentRenderer = rendererMap["masked_sprite"]!!
    }

    fun addDrawable(drawable: Drawable) {
        if (!drawable.visible) return
        drawableList.add(drawable)
    }

    fun render() {
        drawableList.sortBy { it.z }
        for (renderer in rendererMap) {
            renderer.value.setProjectionMatrix(projection.camera.combined)
        }
        gpuCalls += rawRender()
        drawableList.clear()
    }

    private fun rawRender(): Int {
        var gpuCalls = 0
        for (drawable in drawableList) {
            if (!drawable.visible) continue
            if (currentRenderer.typeHandled === drawable.getType() && !currentRenderer.active) currentRenderer.begin()
            else if(currentRenderer.typeHandled !== drawable.getType()) {
                if (currentRenderer.active) gpuCalls += currentRenderer.end()
                currentRenderer = rendererMap[drawable.getType()]!!
                currentRenderer.begin()
            }
            currentRenderer.draw(drawable)
        }

        if (currentRenderer.active) gpuCalls += currentRenderer.end()
        return gpuCalls
    }

    fun getGpuCallsNo(): Int {
        return gpuCalls
    }

    override fun dispose() {
        for(renderer in rendererMap) {
            renderer.value.dispose()
        }
        rendererMap.clear()
        drawableList.clear()
    }
}