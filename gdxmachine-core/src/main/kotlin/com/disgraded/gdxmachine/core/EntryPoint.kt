package com.disgraded.gdxmachine.core

import com.disgraded.gdxmachine.core.engine.Game

interface EntryPoint {
    fun initialize(game: Game)
    fun destroy()
}