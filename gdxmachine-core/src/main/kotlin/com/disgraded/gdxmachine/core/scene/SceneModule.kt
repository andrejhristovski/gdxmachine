package com.disgraded.gdxmachine.core.scene

import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.engine.module.EngineModule
import com.disgraded.gdxmachine.core.engine.module.ModuleApi

class SceneModule(private val game: Game) : EngineModule {

    private val sceneApi = SceneApi(this)

    override val api: ModuleApi
        get() = sceneApi

    override fun load() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unload() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun update() {

    }
}