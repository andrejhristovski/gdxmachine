package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport

class RenderContext(private val shaderContainer: ShaderContainer, private val virtualWidth: Int,
                    private val virtualHeight: Int) : Disposable {

    class CameraApi(private val camera: OrthographicCamera) {
        var x
            get() = camera.position.x
            set(value) {
                camera.position.x = value
            }

        var y
            get() = camera.position.y
            set(value) {
                camera.position.y = value
            }

        var z
            get() = camera.zoom
            set(value) {
                camera.zoom = value
            }

        fun translate(x: Float, y: Float) = camera.translate(x, y)

        fun rotate(angle: Float) = camera.rotate(angle)
    }

    class Api(private val renderContext: RenderContext) {

        val camera = renderContext.cameraApi

        var visible = true

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

        fun getRenderCalls() : Int = renderContext.renderCalls
        
        var order
            get() = renderContext.order
            set(value) {
                renderContext.order = value
            }
    }

    var order: Int = 0
    private val renderBatch2D = RenderBatch2D(shaderContainer)
    private val drawableList = arrayListOf<Drawable>()
    private var renderCalls = 0

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height


    private val camera = OrthographicCamera()
    private val cameraApi = CameraApi(camera)
    private val viewport = ScalingViewport(Scaling.fit, virtualWidth.toFloat(), virtualHeight.toFloat(), camera)

    val renderApi = Api(this)

    init {
        applyViewport()
    }

    fun render() {
        if (!renderApi.visible) return

        viewport.camera.update()
        renderBatch2D.projectionMatrix = viewport.camera.combined
        renderBatch2D.begin()
        for(drawable in drawableList) {
            renderBatch2D.draw(drawable)
        }
        renderBatch2D.end()
        drawableList.clear()
        renderCalls = renderBatch2D.renderCalls
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        applyViewport()
    }

    override fun dispose() {
        renderBatch2D.dispose()
        drawableList.clear()
    }

    private fun applyViewport() {
        viewport.update(screenWidth, screenHeight)
        viewport.apply()
    }
}