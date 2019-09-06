package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.EntryPoint
import com.disgraded.gdxmachine.core.Context

class SandboxGame : EntryPoint {

    override fun configure(): Config? {
        return Config.apply {
            screenX = 1920
            screenY = 1080
        }
    }

    override fun initialize(context: Context) {
        context.resources.load(InitialAssets::class, true)
        context.scene.set(TestScene::class)
    }

    override fun destroy() {

    }
}