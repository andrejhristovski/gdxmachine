package com.disgraded.gdxmachine.framework

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.graphics.GraphicsModule
import com.disgraded.gdxmachine.framework.input.InputModule
import com.disgraded.gdxmachine.framework.resources.ResourceModule

class GdxRuntime(private val entryPoint: EntryPoint) : ApplicationListener {

    private lateinit var inputModule: InputModule
    private lateinit var  resourceModule: ResourceModule
    private lateinit var  graphicsModule: GraphicsModule

    override fun create() {
        inputModule = InputModule()
        resourceModule = ResourceModule()
        graphicsModule = GraphicsModule()

        inputModule.load()
        resourceModule.load()
        graphicsModule.load()

        Api.input = inputModule.api
        Api.resources = resourceModule.api
        Api.graphics = graphicsModule.api
        entryPoint.initialize()
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
}