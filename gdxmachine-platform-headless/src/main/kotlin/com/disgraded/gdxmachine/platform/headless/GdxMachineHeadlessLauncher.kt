package com.disgraded.gdxmachine.platform.headless

import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.GdxRuntime

class GdxMachineHeadlessLauncher {

    fun run(entryPoint: EntryPoint) {
        HeadlessApplication(GdxRuntime(entryPoint))
    }
}