package com.disgraded.gdxmachine.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ApplicationListener as GdxApplicationListener

/** ApplicationListener class is implementation of libGDX Application Listener class and is used to trigger the
 * main lifecycle events provided by the libGDX */
class ApplicationListener(private val core: Core) : GdxApplicationListener {
    override fun create() = core.load()
    override fun render() = core.update(Gdx.graphics.deltaTime)
    override fun pause() = core.pause()
    override fun resume() = core.resume()
    override fun resize(width: Int, height: Int) = core.resize(width, height)
    override fun dispose() = core.unload()
}