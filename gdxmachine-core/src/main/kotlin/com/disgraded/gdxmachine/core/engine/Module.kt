package com.disgraded.gdxmachine.core.engine

interface Module {
    val api : ModuleApi
    fun load()
    fun unload()
}