package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class Viewport : Disposable {

    class Api(private val viewport: Viewport) {

        var ambientColor = Color.BLACK

        val camera: OrthographicCamera = viewport.projection.camera

        var visible = true

        var z = 0

        fun enableLights() {
            viewport.lightsEnabled = true
        }

        fun disableLights() {
            viewport.lightsEnabled = false
        }

        fun isLightsEnabled(): Boolean = viewport.lightsEnabled

        fun getGPUCalls(): Int = viewport.gpuCalls

        fun draw(drawable: Drawable) {
            if (!drawable.visible) return
            when (drawable.type) {
                Drawable.Type.SPRITE -> viewport.drawableList.add(drawable)
                Drawable.Type.LIGHT -> viewport.lightList.add(drawable as Light)
            }
        }

        fun project(xRatio: Float, yRatio: Float, scaleX: Float, scaleY: Float, worldScaleX: Float = 1f,
                    worldScaleY: Float = 1f) {
            viewport.projection.project(xRatio, yRatio, scaleX, scaleY, worldScaleX, worldScaleY)
        }
    }

    private var gpuCalls = 0
    private var lightsEnabled = false

    private val projection = Projection()
    private val diffuseBatch = DiffuseBatch()
    private val deferredLightBatch = DeferredLightBatch(projection)

    private val drawableList = arrayListOf<Drawable>()
    private val lightList = arrayListOf<Light>()

    val api = Api(this)

    fun render() {
        if(!api.visible) return
        drawableList.sortBy { it.z }
        projection.apply()
        if (lightsEnabled) {
            deferredLightBatch.apply(lightList, api.ambientColor)
            gpuCalls = deferredLightBatch.render(drawableList, projection.camera.combined)
        } else {
            gpuCalls = diffuseBatch.render(drawableList, projection.camera.combined)
        }
        drawableList.clear()
        lightList.clear()
    }

    fun resize(width: Int, height: Int) = projection.resize(width, height)

    fun updateViewport(width: Float, height: Float, scale: Config.Graphics.Scale) {
        projection.update(width, height, scale)
    }

    override fun dispose() {
        diffuseBatch.dispose()
        deferredLightBatch.dispose()
    }
}