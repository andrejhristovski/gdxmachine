package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.Renderer
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteRenderer

class RenderContext(private val virtualWidth: Int, private val virtualHeight: Int) : Disposable {

    class Api(private val renderContext: RenderContext) {

        var visible = true

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

    }

    private val drawableList = arrayListOf<Drawable>()

    private var screenWidth = Gdx.graphics.width
    private var screenHeight = Gdx.graphics.height

    private val camera = OrthographicCamera()
    private val viewport = ScalingViewport(Scaling.fit, virtualWidth.toFloat(), virtualHeight.toFloat(), camera)

    val api = Api(this)

    private val spriteRenderer = SpriteRenderer()

    init {
        applyViewport()
        camera.position.x += virtualWidth / 2
        camera.position.y += virtualHeight / 2
    }

    fun render() {
        viewport.camera.update()
        spriteRenderer.setProjectionMatrix(viewport.camera.combined)
        spriteRenderer.begin()
        for(drawable in drawableList) {
            spriteRenderer.draw(drawable as Sprite)
        }
        spriteRenderer.end()

    }

    private fun getRenderer(type: Drawable.Type): Renderer<out Drawable> {
        return when(type) {
            Drawable.Type.SPRITE -> spriteRenderer
        }
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        applyViewport()
    }

    override fun dispose() {
        drawableList.clear()
    }

    private fun applyViewport() {
        viewport.update(screenWidth, screenHeight)
        viewport.apply()
    }
}