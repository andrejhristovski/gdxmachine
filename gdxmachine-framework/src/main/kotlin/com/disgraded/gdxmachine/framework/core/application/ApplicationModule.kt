package com.disgraded.gdxmachine.framework.core.application

import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.core.Module

class ApplicationModule : Module {

    val loggerMap = hashMapOf<String, Logger>()

    val api = ApplicationApi(this)

    override fun load() {
        Gdx.app.clipboard
    }

    override fun unload() {

    }

    fun getLogger(scope: String): Logger {
        if (!loggerMap.containsKey(scope)) {
            loggerMap[scope] = Logger(scope)
        }
        return loggerMap[scope]!!
    }
}