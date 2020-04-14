package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport

class Layer(val key: String, private var width: Float, private var height: Float, scaling: Scaling):
        ScalingViewport(scaling, width, height, OrthographicCamera()), Disposable {

    private val drawableList = arrayListOf<Drawable>()

    private var renderer: Renderer? = null
    var layerScale: LayerScale? = null

    var visible = true
    var priority = 0

    fun draw(drawable: Drawable) {
        if (drawable.isExecutable()) drawableList.add(drawable)
    }

    fun render(): Int {
        if (renderer == null) throw RuntimeException("Layer [$key] doesn't contain renderer")
        if (!visible) return 0
        apply()
        drawableList.sortBy { it.getOrder() }
        val gpuCalls = renderer!!.render(drawableList, this)
        drawableList.clear()
        return gpuCalls
    }

    override fun update(screenWidth: Int, screenHeight: Int, centerCamera: Boolean) {
        if (layerScale == null) {
            super.update(screenWidth, screenHeight, centerCamera)
        }
        else {
            layerScale!!.apply(this, screenWidth, screenHeight)
        }
    }

    override fun dispose() {
        if (renderer !== null) renderer!!.dispose()
    }

    fun setRenderer(renderer: Renderer) {
        if (this.renderer !== null) {
            this.renderer!!.dispose()
        }
        this.renderer = renderer
    }
}