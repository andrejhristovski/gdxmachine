package com.disgraded.gdxmachine.sandbox.source.entity

import com.disgraded.gdxmachine.core.api.engine.Entity

class TestEntity : Entity("test") {

    override fun initialize() {
        println("entity created")
    }

    override fun update() {

    }

    override fun destroy() {
        println("entity destroyed")
    }
}