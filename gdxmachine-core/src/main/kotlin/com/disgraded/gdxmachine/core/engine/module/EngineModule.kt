package com.disgraded.gdxmachine.core.engine.module

interface EngineModule {
    val api : ModuleApi
    fun load()
    fun unload()
}