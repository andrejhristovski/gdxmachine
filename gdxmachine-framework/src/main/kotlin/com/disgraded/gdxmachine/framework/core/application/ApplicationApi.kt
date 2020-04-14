package com.disgraded.gdxmachine.framework.core.application

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Clipboard

class ApplicationApi(private val applicationModule: ApplicationModule) {

    fun getLogger(scope: Any): Logger = applicationModule.getLogger(scope.javaClass.toString())

    fun terminate() = Gdx.app.exit()

    fun getClipboard(): Clipboard = Gdx.app.clipboard

}