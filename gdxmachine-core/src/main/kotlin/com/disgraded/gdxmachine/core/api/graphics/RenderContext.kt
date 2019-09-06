package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteRenderer

class RenderContext(private val virtualWidth: Int, private val virtualHeight: Int) : Disposable {

    class Api(private val renderContext: RenderContext) {
        var visible = true

        fun draw(drawable2D: Drawable2D) = renderContext.drawableList.add(drawable2D)
    }

    private val drawableList = arrayListOf<Drawable2D>()

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height

    private val camera = OrthographicCamera()
    private val viewport = ScalingViewport(Scaling.fit, virtualWidth.toFloat(), virtualHeight.toFloat(), camera)

    val api = Api(this)

    private val spriteRenderer = SpriteRenderer()

    init {
        applyViewport()
    }

    fun render() {
        drawableList.sortBy { it.z }
        viewport.camera.update()
        spriteRenderer.setProjectionMatrix(viewport.camera.combined)
        spriteRenderer.begin()
        for(drawable in drawableList) {
            if (!drawable.visible) continue
            spriteRenderer.draw(drawable as Sprite)
        }
        spriteRenderer.end()
        drawableList.clear()
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        applyViewport()
    }

    override fun dispose() {
        spriteRenderer.dispose()
        drawableList.clear()
    }

    private fun applyViewport() {
        viewport.update(screenWidth, screenHeight)
        viewport.apply()
    }
}