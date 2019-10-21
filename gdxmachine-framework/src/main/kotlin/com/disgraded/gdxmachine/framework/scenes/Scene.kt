package com.disgraded.gdxmachine.framework.scenes

import com.disgraded.gdxmachine.framework.core.Core
import kotlin.reflect.KClass

abstract class Scene {

    protected val core = Core
    private val sceneManager = SceneManager.getInstance()

    abstract fun initialize()

    abstract fun destroy()

    protected fun setScene(sceneClass: KClass<Scene>) = sceneManager.set(sceneClass)

}