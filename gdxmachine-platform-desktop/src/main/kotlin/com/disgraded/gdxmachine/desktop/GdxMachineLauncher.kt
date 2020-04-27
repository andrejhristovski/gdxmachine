package com.disgraded.gdxmachine.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.framework.core.GdxRuntime

class GdxMachineLauncher {

    fun run(entryPoint: EntryPoint, cfg: Lwjgl3ApplicationConfiguration? = null) {

        var configuration = Lwjgl3ApplicationConfiguration()

        val samples = Lwjgl3ApplicationConfiguration::class.java.getDeclaredField("samples")
        samples.isAccessible = true
        samples.setInt(configuration, 8)

        configuration.setTitle("GdxMachine Desktop Game")
        configuration.setDecorated(true)
        configuration.useVsync(true)
        configuration.setWindowedMode(1280, 720)
        if (cfg !== null) {
            configuration = cfg
        }
        Lwjgl3Application(GdxRuntime(entryPoint), configuration)
    }
}