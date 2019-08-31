package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

class RenderContext(shaderContainer: ShaderContainer) : Disposable {

    class RenderContextApi(private val renderContext: RenderContext) {

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

    }

    val renderApi = RenderContextApi(this)

    private val spriteBatch = SpriteBatch(2000)
    private val drawableList = arrayListOf<Drawable>()

    fun render() {
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

    }

    override fun dispose() {

    }
}