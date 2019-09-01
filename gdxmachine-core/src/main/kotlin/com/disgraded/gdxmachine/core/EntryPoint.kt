package com.disgraded.gdxmachine.core

interface EntryPoint {
    fun configure() : Config?
    fun initialize(context: Context)
    fun destroy()
}