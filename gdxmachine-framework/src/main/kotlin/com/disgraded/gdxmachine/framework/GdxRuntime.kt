package com.disgraded.gdxmachine.framework

import com.badlogic.gdx.ApplicationListener

class GdxRuntime(private val entryPoint: EntryPoint) : ApplicationListener {

    override fun create() {
        entryPoint.initialize()
    }

    override fun render() {
        entryPoint.update()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        entryPoint.destroy()
    }
}