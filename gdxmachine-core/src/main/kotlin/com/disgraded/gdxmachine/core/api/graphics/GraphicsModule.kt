package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.core.Core

class GraphicsModule : Core.Module {

    class GraphicsApi(private val graphicsModule: GraphicsModule) : Core.Api {

        fun getDeltaTime() : Float = Gdx.graphics.deltaTime

        fun getFPS() : Int = Gdx.graphics.framesPerSecond
    }

    override val api: Core.Api = GraphicsApi(this)

    override fun load(core: Core) {

    }

    override fun update(deltaTime: Float) {

    }

    override fun unload() {

    }

    fun resize(width: Int, height: Int) {

    }
}