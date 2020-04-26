package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.math.Vector2
import com.disgraded.gdxmachine.framework.Game
import com.disgraded.gdxmachine.sandbox.source.assets.TestAssets
import com.disgraded.gdxmachine.sandbox.source.scenes.FirstScene

class SandboxGame : Game() {

    override fun run() {
        core.graphics.viewport = Vector2(1280f, 720f)
        core.resources.load(TestAssets::class, true)

        setScene(FirstScene::class)
    }

    override fun shutdown() {

    }

}