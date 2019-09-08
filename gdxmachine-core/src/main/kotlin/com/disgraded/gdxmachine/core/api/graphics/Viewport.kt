package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable

class Viewport : Disposable {

    class Api(private val viewport: Viewport) {

        val camera: OrthographicCamera = viewport.projection.camera

        var visible = true

        var order = 0

        fun getGPUCalls(): Int = viewport.batch.getGpuCallsNo()

        fun draw(drawable: Drawable) = viewport.batch.addDrawable(drawable)
        fun project(xRatio: Float, yRatio: Float, scaleX: Float, scaleY: Float, worldScaleX: Float = 1f,
                    worldScaleY: Float = 1f) {
            viewport.projection.project(xRatio, yRatio, scaleX, scaleY, worldScaleX, worldScaleY)
        }
    }

    private val projection = Projection()
    private val batch = DrawableBatch(projection)
    val api = Api(this)

    fun render() {
        if(!api.visible) return
        projection.apply()
        batch.render()
    }

    fun resize(width: Int, height: Int) = projection.resize(width, height)

    fun updateViewport(width: Float, height: Float, scale: Config.Graphics.Scale) {
        projection.update(width, height, scale)
    }

    override fun dispose() {
        batch.dispose()
    }
}