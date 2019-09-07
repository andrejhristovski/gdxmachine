package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core


class GraphicsModule : Core.Module {

    class GraphicsApi(private val graphicsModule: GraphicsModule) : Core.Api {

        fun getDeltaTime() : Float = Gdx.graphics.deltaTime

        fun getFPS() : Int = Gdx.graphics.framesPerSecond

        fun getContext(name: String = "default") : Viewport.Api = graphicsModule.getContext(name)

        fun clear() = graphicsModule.clear()

        fun resize(width: Float, height: Float, scale: Config.Graphics.Scale = Config.Graphics.Scale.FIT) {
            graphicsModule.viewports.forEach { it.value.updateViewport(width, height, scale) }
        }
    }

    override val api: Core.Api = GraphicsApi(this)

    private val viewports = hashMapOf<String, Viewport>()

    lateinit var config: Config

    override fun load(core: Core, config: Config) {
        this.config = config

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)
        Gdx.gl.glDepthFunc(GL20.GL_ALWAYS)
    }

    override fun update(deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearDepthf(1f)
        Gdx.gl.glClearColor(0f, 0f ,0f, 1f)
        viewports.toList().sortedBy { it.second.api.order }.forEach{ it.second.render() }
    }

    override fun unload() {
        viewports.forEach { it.value.dispose() }
    }

    fun resize(width: Int, height: Int) {

        for (context in viewports) {
            context.value.resize(width, height)
        }
    }

    private fun getContext(name: String): Viewport.Api {
        if (!viewports.containsKey(name)) {
            viewports[name] = Viewport()
            viewports[name]!!.updateViewport(config.graphics.width, config.graphics.height, config.graphics.scale)
        }
        return viewports[name]!!.api
    }

    private fun removeContext(name: String) {
        if (!viewports.containsKey(name)) {
            throw RuntimeException("RenderContext [\"$name\"] doesn't exist!")
        }
        viewports[name]!!.dispose()
        viewports.remove(name)
    }

    private fun clear() {
        for (context in viewports) {
            context.value.dispose()
        }
        viewports.clear()
    }
}