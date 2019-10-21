package com.disgraded.gdxmachine.framework.core

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.core.graphics.GraphicsModule
import com.disgraded.gdxmachine.framework.core.input.InputModule
import com.disgraded.gdxmachine.framework.core.physics.PhysicsModule
import com.disgraded.gdxmachine.framework.core.resources.ResourceModule

class GdxRuntime(private val entryPoint: EntryPoint) : ApplicationListener {

    private val core = Core()

    private lateinit var inputModule: InputModule
    private lateinit var  resourceModule: ResourceModule
    private lateinit var  graphicsModule: GraphicsModule
    private lateinit var physicsModule: PhysicsModule

    override fun create() {
        inputModule = InputModule()
        resourceModule = ResourceModule()
        graphicsModule = GraphicsModule()
        physicsModule = PhysicsModule()

        inputModule.load()
        resourceModule.load()
        graphicsModule.load()
        physicsModule.load()
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
    }

    private fun initializeCore() {
        core.input = inputModule.api
        core.resources = resourceModule.api
        core.graphics = graphicsModule.api
        core.physics = physicsModule.api
    }
}