package com.disgraded.gdxmachine.framework.scenes

import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.framework.core.Core
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class SceneManager private constructor(): Disposable {

    private val core = Core

    private var nextScene: KClass<out Scene>? = null
    private var currentScene : Scene? = null

    companion object {
        private val instance = SceneManager()
        fun getInstance(): SceneManager = instance
    }

    fun set(sceneClass: KClass<out Scene>) {
        if (nextScene == null) {
            nextScene = sceneClass
        }
    }

    fun update() {
        if (nextScene !== null) {
            if (currentScene !== null) {
                reset()
                currentScene!!.destroy()
            }
            currentScene = nextScene!!.createInstance()
            nextScene = null
            currentScene!!.prepare()
            currentScene!!.initialize()
        }
        if (currentScene !== null) {
            currentScene!!.update(core.graphics.getDeltaTime())
        }
    }

    override fun dispose() {
        if (currentScene !== null) {
            currentScene!!.destroy()
        }
    }

    private fun reset() {
        core.input.clear()
        core.graphics.clear()
        core.physics.clear()
        core.engine.clear()
    }
}