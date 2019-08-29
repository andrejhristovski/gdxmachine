package com.disgraded.gdxmachine.core.engine

import com.badlogic.ashley.signals.Listener
import com.badlogic.gdx.ApplicationListener
import com.disgraded.gdxmachine.core.EntryPoint
import com.disgraded.gdxmachine.core.ecs.EcsApi
import com.disgraded.gdxmachine.core.ecs.EcsModule
import com.disgraded.gdxmachine.core.resources.ResourceApi
import com.disgraded.gdxmachine.core.resources.ResourceModule
import com.disgraded.gdxmachine.core.scene.SceneApi
import com.disgraded.gdxmachine.core.scene.SceneModule

class Engine private constructor(private val entryPoint: EntryPoint) : ApplicationListener {

    companion object {

        private lateinit var engine : Engine

        fun get() = engine

        fun boot(entryPoint: EntryPoint) {
            engine = Engine(entryPoint)
        }
    }

    private var game = Game
    private lateinit var ecsModule: EcsModule
    private lateinit var resourceModule: ResourceModule
    private lateinit var sceneModule: SceneModule

    override fun create() {
        ecsModule = EcsModule(game)
        resourceModule = ResourceModule(game)
        sceneModule = SceneModule(game)

        ecsModule.load()
        resourceModule.load()
        sceneModule.load()

        game.ecs = ecsModule.api as EcsApi
        game.resources = resourceModule.api as ResourceApi
        game.scene = sceneModule.api as SceneApi

        entryPoint.initialize(game)

        sceneModule.onReset.add { signal, `object` -> reset() }
    }

    override fun render() {
        ecsModule.update()
        resourceModule.update()
        sceneModule.update()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        sceneModule.destroyCurrentScene()

        entryPoint.destroy()

        ecsModule.unload()
        sceneModule.unload()
        resourceModule.unload()
    }

    fun reset() {
        ecsModule.reset()
    }
}