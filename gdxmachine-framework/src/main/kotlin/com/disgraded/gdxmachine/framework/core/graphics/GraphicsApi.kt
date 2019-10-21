package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.GLVersion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Scaling
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class GraphicsApi(private val graphicsModule: GraphicsModule) {

    var virtualViewport = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

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

    fun createLayer(key: String = "default", width: Float = virtualViewport.x, height: Float = virtualViewport.y,
                       scaling: Scaling = Scaling.fill): Layer
            = graphicsModule.createLayer(key, width, height, scaling)

    fun getLayer(key: String = "default"): Layer = graphicsModule.getLayer(key)

    fun removeLayer(key: String = "default") = graphicsModule.removeLayer(key)

    fun existLayer(key: String = "default"): Boolean = graphicsModule.existLayer(key)

    fun compileShader(key: String, vertex: ShaderData, fragment: ShaderData): Shader
            = graphicsModule.compileShader(key, vertex, fragment)

    fun getShader(key: String): Shader = graphicsModule.getShader(key)

    fun removeShader(key: String) = graphicsModule.removeShader(key)

    fun existShader(key: String): Boolean = graphicsModule.existShader(key)
}