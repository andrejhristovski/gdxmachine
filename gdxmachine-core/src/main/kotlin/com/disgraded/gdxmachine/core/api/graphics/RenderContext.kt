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

        fun transform(x: Int, y: Int, width: Int, height: Int) = renderContext.setBounds(x, y, width, height)
    }

    val renderApi = RenderContextApi(this)

    private val spriteBatch = SpriteBatch(1)
    private val drawableList = arrayListOf<Drawable>()

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height

    private var x = 0
    private var y = 0
    private var width = virtualWidth
    private var height = virtualHeight

    private val camera = OrthographicCamera()
    private val viewport = ScalingViewport(Scaling.none, virtualWidth.toFloat(), virtualHeight.toFloat())

    init {
        applyViewport()
    }

    fun render() {
        if (!renderApi.visible) return

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
        applyViewport()
    }

    override fun dispose() {
        spriteBatch.dispose()
    }

    private fun setBounds(x: Int, y: Int, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        applyViewport()
    }

    private fun applyViewport() {
        viewport.update(screenWidth, screenHeight)
        val x = x.toFloat() / virtualWidth.toFloat() * screenWidth.toFloat()
        val y = y.toFloat() / virtualHeight.toFloat() * screenHeight.toFloat()
        val width = width.toFloat() / virtualWidth.toFloat() * screenWidth.toFloat()
        val height = height.toFloat() / virtualHeight.toFloat() * screenHeight.toFloat()
        println("$x, $y, $width, $height  ")
        viewport.setScreenBounds(x.toInt(), y.toInt(), width.toInt(), height.toInt())
        viewport.apply()
    }


}