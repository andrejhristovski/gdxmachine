package com.disgraded.gdxmachine.android
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.GdxRuntime
import com.badlogic.gdx.backends.android.AndroidApplication as LibGDXAndroidActivity

abstract class GameActivity :  LibGDXAndroidActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initialize(GdxRuntime(run()), configure())
    }

    abstract fun run(): EntryPoint

    fun configure() : AndroidApplicationConfiguration {
        return AndroidApplicationConfiguration()
    }
}