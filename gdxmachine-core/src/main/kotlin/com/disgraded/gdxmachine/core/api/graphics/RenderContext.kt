package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport

class RenderContext(private val shaderContainer: ShaderContainer, private val virtualWidth: Int,
                    private val virtualHeight: Int) : Disposable {

    class RenderContextApi(private val renderContext: RenderContext) {

        var visible = true

        var fit = true

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

        fun getRenderCalls() = renderContext.renderCalls

    }

    val renderApi = RenderContextApi(this)

    private val spriteBatch = SpriteBatch(1)
    private val drawableList = arrayListOf<Drawable>()
    private var renderCalls = 0

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height


    private val camera = OrthographicCamera()
    private val viewport = ScalingViewport(Scaling.fit, virtualWidth.toFloat(), virtualHeight.toFloat(), camera)

    init {
        applyViewport()
    }

    fun render() {
        if (!renderApi.visible) return

        viewport.camera.update()
        spriteBatch.projectionMatrix = viewport.camera.combined
        spriteBatch.begin()
        renderDrawableList()
        spriteBatch.end()
        renderCalls = spriteBatch.renderCalls
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        applyViewport()
    }

    override fun dispose() {
        spriteBatch.dispose()
    }

    private fun applyViewport() {
        viewport.update(screenWidth, screenHeight)
        viewport.apply()
    }

    private fun renderDrawableList() {
        for (drawable in drawableList) {
            if (!drawable.visible) continue
            val sizeX = drawable.textureRegion.regionWidth.toFloat()
            val sizeY = drawable.textureRegion.regionHeight.toFloat()
            val originX = sizeX * drawable.scaleX * drawable.pivotX
            val originY = sizeY * drawable.scaleY * drawable.pivotY
            val posX = drawable.x - originX
            val posY = drawable.y - originY

            spriteBatch.draw(drawable.textureRegion, posX, posY, originX, originY, sizeX, sizeY, drawable.scaleX,
                    drawable.scaleY, drawable.rotation)
        }
        drawableList.clear()
    }


}