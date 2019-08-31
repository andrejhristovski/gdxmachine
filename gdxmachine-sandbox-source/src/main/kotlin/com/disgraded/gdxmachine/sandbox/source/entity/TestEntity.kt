package com.disgraded.gdxmachine.sandbox.source.entity

import com.disgraded.gdxmachine.core.api.engine.Entity
import com.disgraded.gdxmachine.sandbox.source.component.ExampleComponent

class TestEntity : Entity("test") {

    override fun initialize() {
        println("entity created")
        add(ExampleComponent())
    }

    override fun update() {

    }

    override fun destroy() {
        println("entity destroyed")
    }
}