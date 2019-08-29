package com.disgraded.gdxmachine.core.scene

import com.badlogic.ashley.signals.Signal
import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.engine.module.EngineModule
import com.disgraded.gdxmachine.core.engine.module.ModuleApi
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class SceneModule(private val game: Game) : EngineModule {

    private val sceneApi = SceneApi(this)

    override val api: ModuleApi
        get() = sceneApi


    val onReset = Signal<Boolean>()

    private var nextScene: KClass<out Scene>? = null
    private var currentScene : Scene? = null

    override fun load() {

    }

    override fun unload() {
        if (currentScene !== null) currentScene!!.destroy()
        currentScene = null
        nextScene = null
    }

    fun update() {
        if (nextScene !== null) {
            if (currentScene !== null) {
                currentScene!!.destroy()
                onReset.dispatch(true)
            }
            currentScene = nextScene!!.createInstance()
            nextScene = null
            currentScene!!.initialize(game)
        }
        if (currentScene !== null) {
            currentScene!!.update()
        }
    }

    fun setScene(sceneClass: KClass<out Scene>) {
        if (nextScene == null) {
            nextScene = sceneClass
        }
    }
}