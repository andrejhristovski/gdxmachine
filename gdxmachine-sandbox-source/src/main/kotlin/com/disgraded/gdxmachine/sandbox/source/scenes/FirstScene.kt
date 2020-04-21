package com.disgraded.gdxmachine.sandbox.source.scenes

import com.disgraded.gdxmachine.framework.scenes.Scene
import com.disgraded.gdxmachine.framework.systems.Render2DSystem
import com.disgraded.gdxmachine.sandbox.source.entities.TestSprite

class FirstScene : Scene() {

    override fun prepare() {
        core.engine.systems.add(Render2DSystem::class)
    }

    override fun initialize() {
        core.engine.add(TestSprite())
    }

    override fun update(deltaTime: Float) {

    }

    override fun destroy() {

    }
}