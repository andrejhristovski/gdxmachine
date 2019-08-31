package com.disgraded.gdxmachine.sandbox.source.system

import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.core.api.engine.Entity
import com.disgraded.gdxmachine.core.api.engine.System
import com.disgraded.gdxmachine.sandbox.source.component.ExampleComponent

class TestSystem : System() {

    override fun initialize(): Family {
        println("system initialized")
        return Family.all(ExampleComponent::class.java).get()
    }

    override fun execute(entityList: ArrayList<Entity>, deltaTime: Float) {

    }

    override fun destroy() {
        println("system destroyed")
    }
}