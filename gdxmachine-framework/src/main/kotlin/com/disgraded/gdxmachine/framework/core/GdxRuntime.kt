package com.disgraded.gdxmachine.framework.core

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.core.application.ApplicationModule
import com.disgraded.gdxmachine.framework.core.audio.AudioModule
import com.disgraded.gdxmachine.framework.core.engine.EngineModule
import com.disgraded.gdxmachine.framework.core.graphics.GraphicsModule
import com.disgraded.gdxmachine.framework.core.input.InputModule
import com.disgraded.gdxmachine.framework.core.network.NetworkModule
import com.disgraded.gdxmachine.framework.core.physics.PhysicsModule
import com.disgraded.gdxmachine.framework.core.resources.ResourceModule

class GdxRuntime(private val entryPoint: EntryPoint) : ApplicationListener {

    private lateinit var inputModule: InputModule
    private lateinit var  resourceModule: ResourceModule
    private lateinit var  graphicsModule: GraphicsModule
    private lateinit var physicsModule: PhysicsModule
    private lateinit var networkModule: NetworkModule
    private lateinit var applicationModule: ApplicationModule
    private lateinit var audioModule: AudioModule
    private lateinit var engineModule: EngineModule

    override fun create() {
        inputModule = InputModule()
        resourceModule = ResourceModule()
        graphicsModule = GraphicsModule()
        physicsModule = PhysicsModule()
        networkModule = NetworkModule()
        applicationModule = ApplicationModule()
        audioModule = AudioModule()
        engineModule = EngineModule()

        inputModule.load()
        resourceModule.load()
        graphicsModule.load()
        physicsModule.load()
        networkModule.load()
        applicationModule.load()
        audioModule.load()
        engineModule.load()
        initializeCore()
        entryPoint.initialize()
    }

    override fun render() {
        applicationModule.update()
        resourceModule.update()
        graphicsModule.update()
        physicsModule.update()
        engineModule.update()
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
        audioModule.unload()
        engineModule.unload()
    }

    private fun initializeCore() {
        Core.input = inputModule.api
        Core.resources = resourceModule.api
        Core.graphics = graphicsModule.api
        Core.physics = physicsModule.api
        Core.network = networkModule.api
        Core.app = applicationModule.api
        Core.audio = audioModule.api
        Core.engine = engineModule.api
    }
}