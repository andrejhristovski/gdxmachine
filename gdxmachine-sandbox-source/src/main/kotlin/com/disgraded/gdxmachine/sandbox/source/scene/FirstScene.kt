package com.disgraded.gdxmachine.sandbox.source.scene

import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.scene.Scene
import com.disgraded.gdxmachine.sandbox.source.entity.TestEntity
import com.disgraded.gdxmachine.sandbox.source.system.TestSystem

class FirstScene : Scene {

    override fun initialize(context: Context) {
        println("test 1")
        context.engine.add(TestEntity())
        context.engine.addSystem(TestSystem())
    }

    override fun update(deltaTime: Float) {

    }

    override fun destroy() {
        println("end test 1")
    }
}