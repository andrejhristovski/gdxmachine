package com.disgraded.gdxmachine.core.api.scene

import com.disgraded.gdxmachine.core.Context

abstract class Scene {

    lateinit var context: Context

    abstract fun initialize()

    abstract fun update(deltaTime: Float)

    abstract fun pause()

    abstract fun resume()

    abstract fun destroy()

}