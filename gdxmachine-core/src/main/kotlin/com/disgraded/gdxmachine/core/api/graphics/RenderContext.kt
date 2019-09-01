package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport

class RenderContext(private val shaderContainer: ShaderContainer) : Disposable {

    class RenderContextApi(private val renderContext: RenderContext) {

        private val camera
            get() = renderContext.camera

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

    }

    val renderApi = RenderContextApi(this)

    private val spriteBatch = SpriteBatch(2000)
    private val drawableList = arrayListOf<Drawable>()

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(100f, 100f, camera)

    init {

    }

    fun render() {
        viewport.apply()
        spriteBatch.projectionMatrix = viewport.camera.combined
        spriteBatch.begin()
        for (drawable in drawableList) {
            spriteBatch.draw(drawable.textureRegion, drawable.x, drawable.y, drawable.pivotX,
                    drawable.pivotY, drawable.sizeX, drawable.sizeY, drawable.scaleX, drawable.scaleY,
                    drawable.rotation)
        }
        spriteBatch.end()
        drawableList.clear()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {

    }
}