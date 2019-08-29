package com.disgraded.gdxmachine.core.scene

import com.disgraded.gdxmachine.core.engine.Game

interface Scene {

    fun initialize(game: Game)

    fun update()

    fun destroy()

}
