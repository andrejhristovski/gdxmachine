package com.disgraded.gdxmachine.framework

import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.framework.scenes.SceneManager

abstract class Game: EntryPoint {

    protected val core = Core
    private val sceneManager = SceneManager.getInstance()

    override fun initialize() {

    }

    override fun update(deltaTime: Float) {
        sceneManager.update()
    }

    override fun destroy() {
        sceneManager.dispose()
    }

    abstract fun start()

    abstract fun shutdown()

}