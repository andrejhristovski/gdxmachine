package com.disgraded.gdxmachine.core.ecs

import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.engine.module.EngineModule
import com.disgraded.gdxmachine.core.engine.module.ModuleApi

class EcsModule(private val game: Game) : EngineModule {

    private val ecsApi = EcsApi(this)

    override val api: ModuleApi
        get() = ecsApi

    override fun load() {

    }

    override fun unload() {

    }

    fun update() {
        
    }
}