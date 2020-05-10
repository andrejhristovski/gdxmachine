package com.disgraded.gdxmachine.framework.core.application

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Clipboard

class ApplicationApi(private val applicationModule: ApplicationModule) {

    class Memory(private val applicationModule: ApplicationModule) {

        val max: Long
            get() = applicationModule.maxMemory

        val heap: Long
            get() = Gdx.app.javaHeap / 1000000

        val native: Long
            get() = Gdx.app.nativeHeap / 1000000

        val total: Long
            get() = heap + native
    }

    val memory = Memory(applicationModule)

    fun getLogger(scope: Any): Logger = applicationModule.getLogger(scope.javaClass.toString())

    fun terminate() = Gdx.app.exit()

    fun getClipboard(): Clipboard = Gdx.app.clipboard

}