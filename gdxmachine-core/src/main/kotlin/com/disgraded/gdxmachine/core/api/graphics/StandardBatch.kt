package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.*

class StandardBatch(private val projection: Projection): Disposable {

    private val rendererMap = hashMapOf<String, Renderer>()
    private var currentRenderer: Renderer
    private val drawableList = arrayListOf<Drawable>()
    var lightsEnabled = false

    init {
        rendererMap["sprite"] = SpriteRenderer()
        rendererMap["masked_sprite"] = MaskedSpriteRenderer()

        currentRenderer = rendererMap["masked_sprite"]!!
    }

    fun addDrawable(drawable: Drawable) {
        if (!drawable.visible) return
        drawableList.add(drawable)
    }

    fun render(): Int {
        var gpuCalls = 0
        drawableList.sortBy { it.z }
        projection.apply()
        for (renderer in rendererMap) {
            renderer.value.setProjectionMatrix(projection.camera.combined)
        }

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
        drawableList.clear()
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