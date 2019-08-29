package com.disgraded.gdxmachine.android
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication as LibGDXAndroidActivity

abstract class GameActivity :  LibGDXAndroidActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("asd")
    }
}