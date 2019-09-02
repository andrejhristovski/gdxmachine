package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core

class GraphicsModule : Core.Module {

    class GraphicsApi(private val graphicsModule: GraphicsModule) : Core.Api {

        fun getDeltaTime() : Float = Gdx.graphics.deltaTime

        fun getFPS() : Int = Gdx.graphics.framesPerSecond

        fun getContext(name: String = "default") : RenderContext.Api = graphicsModule.getContext(name)

        fun clear() = graphicsModule.clear()
    }

    override val api: Core.Api = GraphicsApi(this)

    private val contexts = hashMapOf<String, RenderContext>()
    private lateinit var shaderContainer : ShaderContainer

    lateinit var config: Config

    override fun load(core: Core, config: Config) {
        this.config = config
        shaderContainer = ShaderContainer()
    }

    override fun update(deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(0f, 0f ,0f, 1f)
        for (context in contexts.toList().sortedByDescending { it.second.order }) {
            context.second.render()
        }
    }

    override fun unload() {

    }

    fun resize(width: Int, height: Int) {

        for (context in contexts) {
            context.value.resize(width, height)
        }
    }

    private fun getContext(name: String): RenderContext.Api {
        if (!contexts.containsKey(name)) {
            contexts[name] = RenderContext(shaderContainer, config.screenX, config.screenY)
        }
        return contexts[name]!!.renderApi
    }

    private fun removeContext(name: String) {
        if (!contexts.containsKey(name)) {
            throw RuntimeException("RenderContext [\"$name\"] doesn't exist!")
        }
        contexts[name]!!.dispose()
        contexts.remove(name)
    }

    private fun clear() {
        for (context in contexts) {
            context.value.dispose()
        }
        contexts.clear()
    }
}