package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.disgraded.gdxmachine.framework.core.Core

class Layer(val key: String, private var width: Float, private var height: Float, scaling: Scaling):
        ScalingViewport(scaling, width, height, OrthographicCamera()), Comparable<Layer>, Disposable {

    private val drawableList = arrayListOf<Drawable>()

    private var renderer: Renderer? = null
    private var layerScale: LayerScale? = null

    var visible = true
    var priority = 0
    var sortable = true

    fun draw(drawable: Drawable) {
        if (drawable.isExecutable()) drawableList.add(drawable)
    }

    fun render(): Int {
        if (renderer == null) throw RuntimeException("Layer [$key] doesn't contain renderer")
        if (!visible) return 0
        if (sortable) {
            drawableList.sort()
        }
        val gpuCalls = renderer!!.render(drawableList, this)
        drawableList.clear()
        return gpuCalls
    }

    override fun update(screenWidth: Int, screenHeight: Int, centerCamera: Boolean) {
        setWorldSize(Core.graphics.viewport.x, Core.graphics.viewport.y)
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

    fun setScale(layerScale: LayerScale) {
        this.layerScale = layerScale
    }

    override fun compareTo(other: Layer): Int = priority.compareTo(other.priority)
}