package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core

class GraphicsModule : Core.Module {

    class GraphicsApi(private val graphicsModule: GraphicsModule) : Core.Api {

        fun getDeltaTime() : Float = Gdx.graphics.deltaTime

        fun getFPS() : Int = Gdx.graphics.framesPerSecond

        fun getContext(name: String = "default") : RenderContext.RenderContextApi = graphicsModule.getContext(name)

        fun clear() = graphicsModule.clear()
    }

    override val api: Core.Api = GraphicsApi(this)

    private val contexts = hashMapOf<String, RenderContext>()
    private val shaderContainer = ShaderContainer

    override fun load(core: Core, config: Config) {

    }

    override fun update(deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(0f, 0f ,0f, 1f)
        for (context in contexts) {
            context.value.render()
        }
    }

    override fun unload() {

    }

    fun resize(width: Int, height: Int) {

        for (context in contexts) {
            context.value.resize(width, height)
        }
    }

    private fun getContext(name: String): RenderContext.RenderContextApi {
        if (!contexts.containsKey(name)) {
            contexts[name] = RenderContext(shaderContainer)
        }
        return contexts[name]!!.renderApi
    }

    private fun removeContext(name: String) {
        if (!contexts.containsKey(name)) {
            throw RuntimeException("")
        }
        contexts[name]!!.dispose()
    }

    private fun clear() {
        for (context in contexts) {
            context.value.dispose()
        }
        contexts.clear()
    }
}