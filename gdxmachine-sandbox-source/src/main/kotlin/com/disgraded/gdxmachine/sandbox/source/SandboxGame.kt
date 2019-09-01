package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.EntryPoint
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.sandbox.source.asset.InitialAssets
import com.disgraded.gdxmachine.sandbox.source.scene.FirstScene

class SandboxGame : EntryPoint {

    override fun configure(): Config? {
        return null
    }

    override fun initialize(context: Context) {
        println("game was initialized")
        context.resources.load(InitialAssets::class, true)
        context.scene.set(FirstScene::class)
    }

    override fun destroy() {
        println("game was destroyed")
    }
}