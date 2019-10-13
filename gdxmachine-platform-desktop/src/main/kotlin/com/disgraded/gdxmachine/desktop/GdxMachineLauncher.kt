package com.disgraded.gdxmachine.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.GdxRuntime

class GdxMachineLauncher {

    fun run(entryPoint: EntryPoint, cfg: Lwjgl3ApplicationConfiguration? = null) {
        var configuration = Lwjgl3ApplicationConfiguration()
        configuration.setTitle("GdxMachine Desktop Game")
        if (cfg !== null) {
            configuration = cfg
        }
        Lwjgl3Application(GdxRuntime(entryPoint), configuration)
    }
}