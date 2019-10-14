package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.disgraded.gdxmachine.framework.Module

class GraphicsModule : Module {

    val gpuCalls: Int = 0 //TODO: implement this

    val api = GraphicsApi(this)

    override fun load() { }

    override fun unload() = clear()

    fun update(deltaTime: Float) {
        Gdx.gl.glClearColor(0f, 0f ,0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        var glCalls = 0
    }

    fun resize(width: Int, height: Int) {
        api.onResize.dispatch(null)
    }

    fun clear() {

    }
}