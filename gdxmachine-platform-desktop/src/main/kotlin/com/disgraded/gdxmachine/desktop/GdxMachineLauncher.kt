package com.disgraded.gdxmachine.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.framework.core.GdxRuntime

class GdxMachineLauncher {

    enum class AA (val samples: Int) {
        MSAA_2X(2), MSAA_4X(4), MSAA_8X(8), MSAA_16X(16)
    }

    var title = "GdxMachine Game"

    var antiAliasing = AA.MSAA_2X
    var vSync = true

    var decorated = false
    var width = 1280
    var height = 720

    fun run(entryPoint: EntryPoint) {

        val configuration = Lwjgl3ApplicationConfiguration()

        val samples = Lwjgl3ApplicationConfiguration::class.java.getDeclaredField("samples")
        samples.isAccessible = true
        samples.setInt(configuration, antiAliasing.samples)

        configuration.setTitle(title)
        configuration.setDecorated(decorated)
        configuration.useVsync(vSync)
        configuration.setWindowedMode(width, height)
        configuration.setAutoIconify(true)
        Lwjgl3Application(GdxRuntime(entryPoint), configuration)
    }
}