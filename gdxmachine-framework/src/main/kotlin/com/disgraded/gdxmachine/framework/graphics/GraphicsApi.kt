package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.GLVersion

class GraphicsApi(private val graphicsModule: GraphicsModule) {

    val onResize = Signal<Any>()

    fun getWidth() = Gdx.graphics.width

    fun getHeight() = Gdx.graphics.height

    fun getGPUCalls(): Int = graphicsModule.gpuCalls

    fun getDeltaTime(): Float = Gdx.graphics.deltaTime

    fun getFPS(): Int = Gdx.graphics.framesPerSecond

    fun clear() = graphicsModule.clear()

    fun enableVSync(enable: Boolean) = Gdx.graphics.setVSync(enable)

    fun getOpenGLVersion(): GLVersion = Gdx.graphics.glVersion

    fun isFullscreen(): Boolean = Gdx.graphics.isFullscreen

    fun setFullScreenMode() = Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.monitor))

    fun setWindowedMode(width: Int, height: Int) = Gdx.graphics.setWindowedMode(width, height)

    fun ppcX(): Float = Gdx.graphics.ppcX

    fun ppcY(): Float = Gdx.graphics.ppcY

    fun ppiX(): Float = Gdx.graphics.ppiX

    fun ppiY(): Float = Gdx.graphics.ppiY

    fun getDensity(): Float = Gdx.graphics.density

    fun setDecoration(enable: Boolean) = Gdx.graphics.setUndecorated(!enable)

    fun setResizable(enable: Boolean) = Gdx.graphics.setResizable(enable)
}