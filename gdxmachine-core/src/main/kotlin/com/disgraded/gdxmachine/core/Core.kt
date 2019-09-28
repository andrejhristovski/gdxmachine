package com.disgraded.gdxmachine.core

import com.badlogic.ashley.signals.Signal
import com.disgraded.gdxmachine.core.api.engine.EngineModule
import com.disgraded.gdxmachine.core.api.graphics.GraphicsModule
import com.disgraded.gdxmachine.core.api.input.InputModule
import com.disgraded.gdxmachine.core.api.resource.ResourceModule
import com.disgraded.gdxmachine.core.api.scene.SceneModule

class Core private constructor(private val entryPoint: EntryPoint) {

    interface Api
    interface Module {
        val api : Api
        fun load(core: Core, config: Config)
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

    private val resourceModule = ResourceModule()
    private val engineModule = EngineModule()
    private val sceneModule = SceneModule()
    private val graphicsModule = GraphicsModule()
    private val inputModule = InputModule()

    fun load() {
        context.resources = resourceModule.api as ResourceModule.Api
        context.engine = engineModule.api as EngineModule.Api
        context.scene = sceneModule.api as SceneModule.Api
        context.graphics = graphicsModule.api as GraphicsModule.Api
        context.input = inputModule.api as InputModule.Api

        var config = entryPoint.configure()
        if (config == null) config = Config

        resourceModule.load(this, config)
        engineModule.load(this, config)
        sceneModule.load(this, config)
        graphicsModule.load(this, config)
        inputModule.load(this, config)

        entryPoint.initialize(context)
    }

    fun unload() {
        engineModule.unload()
        sceneModule.destroy()
        entryPoint.destroy()

        sceneModule.unload()
        resourceModule.unload()
        graphicsModule.unload()
        inputModule.unload()
    }

    fun update(deltaTime: Float) {
        resourceModule.update(deltaTime)
        engineModule.update(deltaTime)
        sceneModule.update(deltaTime)
        inputModule.update(deltaTime)
        graphicsModule.update(deltaTime)
    }

    fun pause() {
        sceneModule.pause()
    }

    fun resume() {
        sceneModule.resume()
    }

    fun resize(width: Int, height: Int) {
        graphicsModule.resize(width, height)
    }

    fun reset() {
        engineModule.reset()
        graphicsModule.clear()
    }
}