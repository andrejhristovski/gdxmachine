package com.disgraded.gdxmachine.sandbox.desktop

import com.disgraded.gdxmachine.desktop.GdxMachineLauncher
import com.disgraded.gdxmachine.sandbox.source.SandboxGame

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        GdxMachineLauncher().apply {

        }.run(SandboxGame())
    }
}