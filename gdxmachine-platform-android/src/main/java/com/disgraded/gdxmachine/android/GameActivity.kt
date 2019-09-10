package com.disgraded.gdxmachine.android
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.disgraded.gdxmachine.core.Core
import com.disgraded.gdxmachine.core.EntryPoint
import com.badlogic.gdx.backends.android.AndroidApplication as LibGDXAndroidActivity

abstract class GameActivity :  LibGDXAndroidActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Core.run(run())
        initialize(Core.appListener, AndroidApplicationConfiguration().apply {
            useGLSurfaceView20API18 = true
        })

    }

    abstract fun run(): EntryPoint
}