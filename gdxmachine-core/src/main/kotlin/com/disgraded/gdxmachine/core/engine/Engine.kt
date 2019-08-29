package com.disgraded.gdxmachine.core.engine

import com.badlogic.gdx.ApplicationListener
import com.disgraded.gdxmachine.core.EntryPoint
import com.disgraded.gdxmachine.core.resources.ResourceApi
import com.disgraded.gdxmachine.core.resources.ResourceModule

class Engine private constructor(private val entryPoint: EntryPoint) : ApplicationListener {

    companion object {

        private lateinit var engine : Engine

        fun get() = engine

        fun boot(entryPoint: EntryPoint) {
            engine = Engine(entryPoint)
        }
    }

    private var game = Game
    private lateinit var resourceModule: ResourceModule

    override fun create() {
        resourceModule = ResourceModule(game)

        resourceModule.load()

        game.resources = resourceModule.api as ResourceApi

        entryPoint.initialize(game)
    }

    override fun render() {
        resourceModule.update()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        entryPoint.destroy()

        resourceModule.unload()
    }
}