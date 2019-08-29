package com.disgraded.gdxmachine.sandbox.source.entity

import com.disgraded.gdxmachine.core.ecs.Entity
import com.disgraded.gdxmachine.core.engine.Game

class TestEntity : Entity() {

    override fun initialize(game: Game) {
        println("entity initialized")
    }

    override fun update() {

    }

    override fun destroy() {
        println("entity destroyed")
    }
}