package com.disgraded.gdxmachine.core.api.scene

import com.disgraded.gdxmachine.core.Context

interface Scene {

    fun initialize(context: Context)

    fun update(deltaTime: Float)

    fun destroy()

}