package com.disgraded.gdxmachine.framework

interface EntryPoint {

    fun initialize()

    fun update(deltaTime: Float)

    fun destroy()

}