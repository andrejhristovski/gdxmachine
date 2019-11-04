package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.core.Module

class EngineModule : Module {

    val api = EngineApi()

    override fun load() {}

    override fun unload() {}

    fun update() = api.update(Gdx.graphics.deltaTime)
}