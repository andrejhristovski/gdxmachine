package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.utils.Disposable

class RenderContext(shaderContainer: ShaderContainer) : Disposable {

    class RenderContextApi(private val renderContext: RenderContext) {

        fun draw(drawable: Drawable) = renderContext.drawableList.add(drawable)

    }

    val renderApi = RenderContextApi(this)

    private val renderBatch = RenderBatch(shaderContainer)
    private val drawableList = arrayListOf<Drawable>()

    fun render() {
        renderBatch.begin()
        for (drawable in drawableList) {
            renderBatch.draw(drawable)
        }
        renderBatch.end()
        drawableList.clear()
    }

    fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }
}