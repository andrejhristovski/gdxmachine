package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.core.Module

class EngineModule : Module {

    val engine = Engine()
    val api = EngineApi(engine)

    override fun load() {}

    override fun unload() {}

    fun update() {
        for (entity in engine.entities) {
            (entity as Entity).update(Gdx.graphics.deltaTime)
        }
        engine.update(Gdx.graphics.deltaTime)
    }
}