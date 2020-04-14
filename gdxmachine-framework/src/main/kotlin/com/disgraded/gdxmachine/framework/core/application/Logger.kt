package com.disgraded.gdxmachine.framework.core.application

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx

class Logger(private val scope: String, var level: LoggerLevel = LoggerLevel.INFO) {

    init {
        Gdx.app.logLevel = Application.LOG_DEBUG
    }

    fun error(message: String, throwable: Throwable? = null) {
        if (level.value < 1) return
        if (throwable == null) {
            Gdx.app.error(scope, message)
        } else {
            Gdx.app.error(scope, message, throwable)
        }
    }

    fun info(message: String, throwable: Throwable? = null) {
        if (level.value < 2) return
        if (throwable == null) {
            Gdx.app.log(scope, message)
        } else {
            Gdx.app.log(scope, message, throwable)
        }
    }

    fun debug(message: String, throwable: Throwable? = null) {
        if (level.value < 3) return
        if (throwable == null) {
            Gdx.app.debug(scope, message)
        } else {
            Gdx.app.debug(scope, message, throwable)
        }
    }

}