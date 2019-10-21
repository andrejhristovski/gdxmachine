package com.disgraded.gdxmachine.framework.core

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.core.application.ApplicationModule
import com.disgraded.gdxmachine.framework.core.graphics.GraphicsModule
import com.disgraded.gdxmachine.framework.core.input.InputModule
import com.disgraded.gdxmachine.framework.core.network.NetworkModule
import com.disgraded.gdxmachine.framework.core.physics.PhysicsModule
import com.disgraded.gdxmachine.framework.core.resources.ResourceModule

class GdxRuntime(private val entryPoint: EntryPoint) : ApplicationListener {

    private val core = Core()

    private lateinit var inputModule: InputModule
    private lateinit var  resourceModule: ResourceModule
    private lateinit var  graphicsModule: GraphicsModule
    private lateinit var physicsModule: PhysicsModule
    private lateinit var networkModule: NetworkModule
    private lateinit var applicationModule: ApplicationModule

    override fun create() {
        inputModule = InputModule()
        resourceModule = ResourceModule()
        graphicsModule = GraphicsModule()
        physicsModule = PhysicsModule()
        networkModule = NetworkModule()
        applicationModule = ApplicationModule()

        inputModule.load()
        resourceModule.load()
        graphicsModule.load()
        physicsModule.load()
        networkModule.load()
        applicationModule.load()
        initializeCore()
        entryPoint.initialize(core)
    }

    override fun render() {
        resourceModule.update()
        graphicsModule.update()
        entryPoint.update(Gdx.graphics.deltaTime)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        graphicsModule.resize(width, height)
    }

    override fun dispose() {
        entryPoint.destroy()
        inputModule.unload()
        resourceModule.unload()
        graphicsModule.unload()
        physicsModule.unload()
        networkModule.unload()
        applicationModule.unload()
    }

    private fun initializeCore() {
        core.input = inputModule.api
        core.resources = resourceModule.api
        core.graphics = graphicsModule.api
        core.physics = physicsModule.api
        core.network = networkModule.api
        core.app = applicationModule.api
    }
}