package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.core.EntryPoint
import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.sandbox.source.asset.InitialAssets
import com.disgraded.gdxmachine.sandbox.source.scene.FirstScene

class SandboxGame : EntryPoint {

    override fun initialize(game: Game) {
        println("game was initialized")
        game.resources.load(InitialAssets::class)

        game.scene.set(FirstScene::class)
    }

    override fun destroy() {
        println("game was destroyed")
    }
}