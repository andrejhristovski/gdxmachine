package com.disgraded.gdxmachine.framework.core

interface EntryPoint {

    fun initialize()

    fun update(deltaTime: Float)

    fun destroy()

}