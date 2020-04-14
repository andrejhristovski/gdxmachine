package com.disgraded.gdxmachine.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.framework.core.GdxRuntime

class GdxMachineLauncher {

    fun run(entryPoint: EntryPoint, cfg: Lwjgl3ApplicationConfiguration? = null) {
        var configuration = Lwjgl3ApplicationConfiguration()
        configuration.setTitle("GdxMachine Desktop Game")
        configuration.setWindowedMode(1280, 720)
        configuration.setDecorated(false)
        configuration.useVsync(true)
        if (cfg !== null) {
            configuration = cfg
        }
        Lwjgl3Application(GdxRuntime(entryPoint), configuration)
    }
}