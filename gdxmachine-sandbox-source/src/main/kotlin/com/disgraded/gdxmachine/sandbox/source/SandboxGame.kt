package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.Game
import com.disgraded.gdxmachine.sandbox.source.assets.TestAssets
import com.disgraded.gdxmachine.sandbox.source.scenes.FirstScene

class SandboxGame : Game() {

    override fun run() {
        core.resources.load(TestAssets::class, true)

        setScene(FirstScene::class)
    }

    override fun shutdown() {

    }

}