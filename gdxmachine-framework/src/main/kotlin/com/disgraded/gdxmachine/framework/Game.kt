package com.disgraded.gdxmachine.framework

import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.framework.extras.CoreAssets
import com.disgraded.gdxmachine.framework.scenes.Scene
import com.disgraded.gdxmachine.framework.scenes.SceneManager
import kotlin.reflect.KClass

abstract class Game: EntryPoint {

    protected val core = Core
    private val sceneManager = SceneManager.getInstance()

    override fun initialize() {
        core.resources.load(CoreAssets::class, true)
        run()
    }

    override fun update(deltaTime: Float) {
        sceneManager.update()
    }

    override fun destroy() {
        shutdown()
        sceneManager.dispose()
    }

    abstract fun run()

    abstract fun shutdown()

    protected fun setScene(sceneClass: KClass<out Scene>) = sceneManager.set(sceneClass)

}