package com.disgraded.gdxmachine.sandbox.source.scene

import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.scene.Scene
import com.disgraded.gdxmachine.sandbox.source.entity.TestEntity

class FirstScene : Scene {

    override fun initialize(game: Game) {
        println("hey")
        game.ecs.add(TestEntity())
    }

    override fun update() {
//        println("update")
    }

    override fun destroy() {
        println("destroy")
    }
}