package com.disgraded.gdxmachine.sandbox.desktop

import com.disgraded.gdxmachine.desktop.GdxMachineLauncher
import com.disgraded.gdxmachine.sandbox.source.SandboxGame

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        GdxMachineLauncher().apply {
            width = 1920
            height = 1080
            decorated = false
            antiAliasing = GdxMachineLauncher.AA.MSAA_16X
//            vSync = false
        }.run(SandboxGame())
    }
}