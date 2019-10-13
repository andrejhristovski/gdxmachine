package com.disgraded.gdxmachine.sandbox.android

import com.disgraded.gdxmachine.android.GameActivity
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.sandbox.source.SandboxGame

class MainActivity : GameActivity() {

    override fun run(): EntryPoint {
        return SandboxGame()
    }
}