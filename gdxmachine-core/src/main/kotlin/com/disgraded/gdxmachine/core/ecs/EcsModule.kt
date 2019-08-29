package com.disgraded.gdxmachine.core.ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.engine.module.EngineModule
import com.disgraded.gdxmachine.core.engine.module.ModuleApi

class EcsModule(val game: Game) : EngineModule {

    private val ecsApi = EcsApi(this)

    override val api: ModuleApi
        get() = ecsApi


    lateinit var engine: Engine

    override fun load() {
        engine = Engine()
    }

    override fun unload() {

    }

    fun update() {
        engine.update(Gdx.graphics.deltaTime)
        for (entity in engine.entities) {
            (entity as Entity).update()
        }
    }

    fun reset() {
        unload()
        load()
    }

}