package com.disgraded.gdxmachine.framework.core

interface EntryPoint {

    fun initialize(core: Core)

    fun update(deltaTime: Float)

    fun destroy()

}