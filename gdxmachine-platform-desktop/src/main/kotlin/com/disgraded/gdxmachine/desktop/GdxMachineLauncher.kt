package com.disgraded.gdxmachine.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.disgraded.gdxmachine.core.EntryPoint
import com.disgraded.gdxmachine.core.Core

class GdxMachineLauncher {

    fun run(entryPoint: EntryPoint) {
        Core.run(entryPoint)
        val cfg = generateConfig()

        Lwjgl3Application(Core.appListener, cfg)
    }

    private fun generateConfig() : Lwjgl3ApplicationConfiguration {
        return Lwjgl3ApplicationConfiguration().apply {
            enableGLDebugOutput(false, System.out)
            setDecorated(true)
            setResizable(true)
            useVsync(false)
            setWindowedMode(1280, 720)
            setIdleFPS(60)
        }
    }
}