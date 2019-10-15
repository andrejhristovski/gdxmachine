package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport

class Viewport(val key: String, private var width: Float, private var height: Float, scaling: Scaling):
        ScalingViewport(scaling, width, height), Disposable {

    private var gpuCalls = 0

    private val drawableList = arrayListOf<Drawable>()

    var renderer: Renderer? = null
    var visible = true
    var priority = 0

    fun draw(drawable: Drawable) = drawableList.add(drawable)

    fun render() {
        if (renderer == null) throw RuntimeException("") // TODO: message
        if (!visible) return
        apply()
        renderer!!.render(drawableList)
        gpuCalls = renderer!!.gpuCalls
        drawableList.clear()
    }

    override fun dispose() {
        if (renderer !== null) renderer!!.dispose()
    }
}