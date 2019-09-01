package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport

class RenderContext(private val shaderContainer: ShaderContainer) : Disposable {

    class RenderContextApi(private val renderContext: RenderContext) {

        private val camera
            get() = renderContext.viewport.camera

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

    }

    val renderApi = RenderContextApi(this)

    private val spriteBatch = SpriteBatch(1)
    private val drawableList = arrayListOf<Drawable>()

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height
    private val viewport = ScalingViewport(Scaling.fit,1280f, 720f)

    private var test = false

    init {
        viewport.update(screenWidth, screenHeight)
        viewport.apply(false)
    }

    fun render() {
        viewport.camera.update()
        spriteBatch.projectionMatrix = viewport.camera.combined
        spriteBatch.begin()
        for (drawable in drawableList) {
            val sizeX = drawable.textureRegion.regionWidth.toFloat()
            val sizeY = drawable.textureRegion.regionHeight.toFloat()
            val originX = sizeX * drawable.scaleX * drawable.pivotX
            val originY = sizeY * drawable.scaleY * drawable.pivotY
            val posX = drawable.x - originX
            val posY = drawable.y - originY

            spriteBatch.draw(drawable.textureRegion, posX, posY, originX, originY, sizeX, sizeY, drawable.scaleX,
                    drawable.scaleY, drawable.rotation)
        }
        spriteBatch.end()
        drawableList.clear()
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        viewport.update(width, height)
    }

    override fun dispose() {
        spriteBatch.dispose()
    }
}