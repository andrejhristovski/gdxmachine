package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.Scaling
import com.disgraded.gdxmachine.framework.Module

class GraphicsModule : Module {

    val gpuCalls: Int = 0 //TODO: implement this

    val api = GraphicsApi(this)

    private val viewportMap = hashMapOf<String, Viewport>()

    override fun load() { }

    override fun unload() = clear()

    fun update() {
        Gdx.gl.glClearColor(0f, 0f ,0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        for ((_, viewport) in viewportMap.toList().sortedBy { it.second.priority }) {
            viewport.render()
        }
    }

    fun resize(width: Int, height: Int) {
        for ((_, viewport) in viewportMap) viewport.update(width, height)
    }

    fun clear() {
        for ((_, viewport) in viewportMap) viewport.dispose()
        viewportMap.clear()
    }

    fun createViewport(key: String, width: Float, height: Float, scaling: Scaling): Viewport {
        if (viewportMap.containsKey(key)) throw RuntimeException("") // TODO: message
        viewportMap[key] = Viewport(key, width, height, scaling)
        return viewportMap[key]!!
    }

    fun getViewport(key: String): Viewport {
        if (!viewportMap.containsKey(key)) throw RuntimeException("") // TODO: message
        return viewportMap[key]!!
    }

    fun removeViewport(key: String) {
        if (!viewportMap.containsKey(key)) throw RuntimeException("") // TODO: message
        viewportMap.remove(key)
    }
}