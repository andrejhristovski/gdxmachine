package com.disgraded.gdxmachine.framework.core.application

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx

class Logger(private val scope: String, var level: LoggerLevel = LoggerLevel.INFO) {

    init {
        Gdx.app.logLevel = Application.LOG_DEBUG
    }

    fun error(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value < 1) return
        if (throwable == null) {
            Gdx.app.error(tag, message)
        } else {
            Gdx.app.error(tag, message, throwable)
        }
    }

    fun info(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value < 2) return
        if (throwable == null) {
            Gdx.app.log(tag, message)
        } else {
            Gdx.app.log(tag, message, throwable)
        }
    }

    fun debug(tag: String, message: String, throwable: Throwable? = null) {
        if (level.value < 3) return
        if (throwable == null) {
            Gdx.app.debug(tag, message)
        } else {
            Gdx.app.debug(tag, message, throwable)
        }
    }

}