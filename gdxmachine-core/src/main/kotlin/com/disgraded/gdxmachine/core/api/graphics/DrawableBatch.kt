package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.renderer.MaskedSpriteRenderer
import com.disgraded.gdxmachine.core.api.graphics.renderer.Renderer2D
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteRenderer

class DrawableBatch(private val projection: Projection): Disposable {

    private val rendererMap = hashMapOf<String, Renderer2D>()
    private var currentRenderer: Renderer2D

    init {
        rendererMap["sprite"] = SpriteRenderer()
        rendererMap["masked_sprite"] = MaskedSpriteRenderer()

        currentRenderer = rendererMap["masked_sprite"]!!
    }

    fun render(drawableList: ArrayList<Drawable2D>) {
        for (renderer in rendererMap) {
            renderer.value.setProjectionMatrix(projection.camera.combined)
        }
        rawRender(drawableList)
    }

    private fun rawRender(drawableList: ArrayList<Drawable2D>): Int {
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

    override fun dispose() {
        for(renderer in rendererMap) {
            renderer.value.dispose()
        }
        rendererMap.clear()
    }
}