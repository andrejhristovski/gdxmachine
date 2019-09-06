package com.disgraded.gdxmachine.android
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy
import com.disgraded.gdxmachine.core.Core
import com.disgraded.gdxmachine.core.EntryPoint
import com.badlogic.gdx.backends.android.AndroidApplication as LibGDXAndroidActivity

abstract class GameActivity :  LibGDXAndroidActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Core.run(run())
        initialize(Core.appListener, AndroidApplicationConfiguration().apply {
            useGLSurfaceView20API18 = true
            hideStatusBar = true
        })
    }

    abstract fun run(): EntryPoint
}