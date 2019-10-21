package com.disgraded.gdxmachine.framework.scenes

import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.framework.core.Core
import kotlin.reflect.KClass

class SceneManager private constructor(): Disposable {

    private val core = Core

    companion object {
        private val instance = SceneManager()
        fun getInstance(): SceneManager = instance
    }

    fun set(sceneClass: KClass<Scene>) {

    }

    fun update() {

    }

    override fun dispose() {

    }

    private fun reset() {

    }
}