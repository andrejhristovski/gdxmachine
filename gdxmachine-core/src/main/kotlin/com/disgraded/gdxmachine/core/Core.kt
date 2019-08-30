package com.disgraded.gdxmachine.core

import com.disgraded.gdxmachine.core.api.engine.EngineModule
import com.disgraded.gdxmachine.core.api.resource.ResourceModule
import com.disgraded.gdxmachine.core.api.scene.SceneModule

class Core private constructor(val entryPoint: EntryPoint) {

    interface Api
    interface Module {
        val api : Api
        fun load(core: Core)
        fun update(deltaTime: Float)
        fun unload()
    }

    companion object {

        lateinit var appListener: ApplicationListener
        private lateinit var core: Core

        fun run(entryPoint: EntryPoint) {
            core = Core(entryPoint)
            appListener = ApplicationListener(core)
        }
    }

    val context = Context

    val resourceModule = ResourceModule()
    val engineModule = EngineModule()
    val sceneModule = SceneModule()

    fun load() {
        context.resources = resourceModule.api as ResourceModule.ResourceApi
        context.engine = engineModule.api as EngineModule.EngineApi
        context.scene = sceneModule.api as SceneModule.SceneApi

        resourceModule.load(this)
        engineModule.load(this)
        sceneModule.load(this)

        entryPoint.initialize(context)
    }

    fun unload() {
        engineModule.unload()
        sceneModule.destroy()
        entryPoint.destroy()

        sceneModule.unload()
        resourceModule.unload()
    }

    fun update(deltaTime: Float, fps: Int) {
        resourceModule.update(deltaTime)
        engineModule.update(deltaTime)
        sceneModule.update(deltaTime)
    }

    fun pause() {

    }

    fun resume() {

    }

    fun resize(width: Int, height: Int) {

    }
}