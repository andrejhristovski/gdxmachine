package com.disgraded.gdxmachine.sandbox.android

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.disgraded.gdxmachine.android.GameActivity
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.sandbox.source.SandboxGame

class MainActivity : GameActivity() {

    override fun run(): EntryPoint {
        return SandboxGame()
    }

    override fun configure(): AndroidApplicationConfiguration {
        return AndroidApplicationConfiguration().apply {

        }
    }
}