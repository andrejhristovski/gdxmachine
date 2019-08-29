package com.disgraded.gdxmachine.sandbox.source.scene

import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.scene.Scene

class FirstScene : Scene {

    override fun initialize(game: Game) {
        println("hey")
    }

    override fun update() {
        println("update")
    }

    override fun destroy() {
        println("destroy")
    }
}