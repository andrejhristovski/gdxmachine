package com.disgraded.gdxmachine.core

import com.disgraded.gdxmachine.core.api.engine.EngineModule
import com.disgraded.gdxmachine.core.api.graphics.GraphicsModule
import com.disgraded.gdxmachine.core.api.input.InputModule
import com.disgraded.gdxmachine.core.api.physics.PhysicsModule
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
    private val physicsModule = PhysicsModule()

    fun load() {
        context.resources = resourceModule.api as ResourceModule.Api
        context.engine = engineModule.api as EngineModule.Api
        context.scene = sceneModule.api as SceneModule.Api
        context.graphics = graphicsModule.api as GraphicsModule.Api
        context.input = inputModule.api as InputModule.Api
        context.physics = physicsModule.api as PhysicsModule.Api

        var config = entryPoint.configure()
        if (config == null) config = Config

        resourceModule.load(this, config)
        engineModule.load(this, config)
        sceneModule.load(this, config)
        graphicsModule.load(this, config)
        inputModule.load(this, config)
        physicsModule.load(this, config)

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
        physicsModule.unload()
    }

    fun update(deltaTime: Float) {
        resourceModule.update(deltaTime)
        engineModule.update(deltaTime)
        sceneModule.update(deltaTime)
        physicsModule.update(deltaTime)
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
        engineModule.clear()
        graphicsModule.clear()
        inputModule.clear()
        physicsModule.clear()
    }
}