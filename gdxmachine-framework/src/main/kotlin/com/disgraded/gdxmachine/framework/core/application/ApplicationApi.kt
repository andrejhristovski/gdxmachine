package com.disgraded.gdxmachine.framework.core.application

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Clipboard

class ApplicationApi(private val applicationModule: ApplicationModule) {

    fun getLogger(scope: String = "global"): Logger = applicationModule.getLogger(scope)

    fun terminate() = Gdx.app.exit()

    fun getClipboard(): Clipboard = Gdx.app.clipboard

}