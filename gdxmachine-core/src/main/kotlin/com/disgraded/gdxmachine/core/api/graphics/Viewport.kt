package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.MaskedSpriteRenderer
import com.disgraded.gdxmachine.core.api.graphics.renderer.Renderer2D
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteRenderer

class Viewport : Disposable {

    class Api(private val viewport: Viewport) {

        val camera: OrthographicCamera = viewport.camera

        var visible = true

        var order = 0

        var glCalls = 0

        fun draw(drawable2D: Drawable2D) = viewport.drawableList.add(drawable2D)

        fun project(xRatio: Float, yRatio: Float, scaleX: Float, scaleY: Float, worldScaleX: Float = 1f,
                    worldScaleY: Float = 1f) {
            viewport.viewportRatioX = xRatio
            viewport.viewportRatioY = yRatio
            viewport.viewportScaleX = scaleX
            viewport.viewportScaleY = scaleY
            viewport.worldScaleX = worldScaleX
            viewport.worldScaleY = worldScaleY
            viewport.shouldUpdateViewport = true
        }
    }

    private val drawableList = arrayListOf<Drawable2D>()

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height
    private var viewportWidth = 0f
    private var viewportHeight = 0f
    private var viewportScale = Config.Graphics.Scale.FIT

    private var viewportRatioX = 0f
    private var viewportRatioY = 0f
    private var viewportScaleX = 1f
    private var viewportScaleY = 1f

    private var worldScaleX = 1f
    private var worldScaleY = 1f

    private var shouldUpdateViewport = false

    private val camera = OrthographicCamera()
    private val viewport = ScalingViewport(Scaling.none, 0f, 0f, camera)

    private val rendererMap = hashMapOf<String, Renderer2D>()
    private var currentRenderer: Renderer2D

    val api = Api(this)

    init {
        rendererMap["sprite"] = SpriteRenderer()
        rendererMap["masked_sprite"] = MaskedSpriteRenderer()

        currentRenderer = rendererMap["masked_sprite"]!!
    }

    fun render() {
        if(!api.visible) return
        applyViewport()
        drawableList.sortBy { it.z }
        viewport.camera.update()

        for (renderer in rendererMap) {
            renderer.value.setProjectionMatrix(camera.combined)
        }

        var gpuCalls = 0

        for (drawable in drawableList) {
            if (!drawable.visible) continue
            if (currentRenderer.typeHandled === drawable.getType() && !currentRenderer.active) currentRenderer.begin()
            else if(currentRenderer.typeHandled !== drawable.getType()) {
                if (currentRenderer.active) gpuCalls += currentRenderer.end()
                currentRenderer = rendererMap[drawable.getType()]!!
                currentRenderer.begin()
            }
            currentRenderer.draw(drawable)
        }

        if (currentRenderer.active) gpuCalls += currentRenderer.end()
        api.glCalls = gpuCalls
        drawableList.clear()

        shouldUpdateViewport = true
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        shouldUpdateViewport = true
    }

    fun updateViewport(width: Float, height: Float, scale: Config.Graphics.Scale) {
        viewportWidth = width
        viewportHeight = height
        viewportScale = scale
        shouldUpdateViewport = true
    }

    override fun dispose() {
        for(renderer in rendererMap) {
            renderer.value.dispose()
        }
        rendererMap.clear()
        drawableList.clear()
    }

    private fun applyViewport() {
        if(!shouldUpdateViewport) return
        if (viewportWidth == 0f || viewportHeight == 0f) {
            viewportWidth = 1280f
            viewportHeight = 720f
        }

        var previewWidth = 0f
        var previewHeight = 0f

        var viewportX = 0
        var viewportY = 0
        var viewportSizeX = 0
        var viewportSizeY = 0

        when(viewportScale) {
            Config.Graphics.Scale.FIT -> {
                val targetRatio: Float = screenHeight.toFloat() / screenWidth
                val sourceRatio: Float = viewportHeight / viewportWidth
                val scale = if (targetRatio > sourceRatio) screenWidth / viewportWidth else screenHeight / viewportHeight
                viewportSizeX = (viewportWidth * scale).toInt()
                viewportSizeY = (viewportHeight * scale).toInt()
                val restX = screenWidth - viewportSizeX
                val restY = screenHeight - viewportSizeY
                viewportX  = (restX / 2 + viewportSizeX * viewportRatioX).toInt()
                viewportY = (restY / 2 + viewportSizeY * viewportRatioY).toInt()
                viewportSizeX = (viewportSizeX.toFloat() * viewportScaleX).toInt()
                viewportSizeY = (viewportSizeY.toFloat() * viewportScaleY).toInt()
                previewWidth = viewportWidth * viewportScaleX * worldScaleX
                previewHeight = viewportHeight * viewportScaleY * worldScaleY
            }

            Config.Graphics.Scale.FILL -> {
                val targetRatio: Float = screenHeight.toFloat() / screenWidth
                val sourceRatio: Float = viewportHeight / viewportWidth
                val scale = if (targetRatio > sourceRatio) screenWidth / viewportWidth else screenHeight / viewportHeight
                viewportX = (screenWidth * viewportRatioX).toInt()
                viewportY = (screenHeight * viewportRatioY).toInt()
                viewportSizeX = (screenWidth * viewportScaleX).toInt()
                viewportSizeY = (screenHeight * viewportScaleY).toInt()
                previewWidth = screenWidth.toFloat() * (1f / scale) * worldScaleX * viewportScaleX
                previewHeight = screenHeight.toFloat() * (1f / scale) * worldScaleY * viewportScaleY
            }
        }


        viewport.setWorldSize(previewWidth, previewHeight)
        viewport.setScreenBounds(viewportX, viewportY, viewportSizeX, viewportSizeY)
        viewport.apply()
    }
}