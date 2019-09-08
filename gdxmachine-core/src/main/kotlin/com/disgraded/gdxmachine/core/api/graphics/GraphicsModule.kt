package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core


class GraphicsModule : Core.Module {

    class Api(private val graphicsModule: GraphicsModule) : Core.Api {

        var glCalls = 0

        fun getDeltaTime() : Float = Gdx.graphics.deltaTime

        fun getFPS() : Int = Gdx.graphics.framesPerSecond

        fun createViewport(name: String = "default") : Viewport.Api = graphicsModule.createViewport(name)

        fun getViewport(name: String = "default") : Viewport.Api = graphicsModule.getViewport(name)

        fun deleteViewport(name: String = "default") = graphicsModule.deleteViewport(name)

        fun clear() = graphicsModule.clear()

        fun resize(width: Float, height: Float, scale: Config.Graphics.Scale = Config.Graphics.Scale.FIT) {
            graphicsModule.viewports.forEach { it.value.updateViewport(width, height, scale) }
        }
    }

    override val api: Core.Api = Api(this)

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
        var glCalls = 0
        viewports.toList().sortedBy { it.second.api.order }.forEach{
            it.second.render()
            glCalls += it.second.api.getGPUCalls()
        }
        (api as Api).glCalls = glCalls
    }

    override fun unload() {
        viewports.forEach {
            it.value.dispose()
        }
        viewports.clear()
    }

    fun resize(width: Int, height: Int) {

        for (context in viewports) {
            context.value.resize(width, height)
        }
    }

    private fun createViewport(name: String): Viewport.Api {
        if (viewports.containsKey(name)) throw RuntimeException("Viewport with the name \"$name\" it's already created!")
        viewports[name] = Viewport()
        viewports[name]!!.updateViewport(config.graphics.width, config.graphics.height, config.graphics.scale)
        return viewports[name]!!.api
    }

    private fun getViewport(name: String): Viewport.Api {
        if (viewports.containsKey(name)) {
            return viewports[name]!!.api
        }
        throw RuntimeException("Viewport with the name \"$name\" doesn't exist!")
    }

    private fun deleteViewport(name: String) {
        if (!viewports.containsKey(name)) throw RuntimeException("Viewport with the name \"$name\" doesn't exist!")
        viewports[name]!!.dispose()
        viewports.remove(name)
    }

    fun clear() {
        for (context in viewports) {
            context.value.dispose()
        }
        viewports.clear()
    }
}