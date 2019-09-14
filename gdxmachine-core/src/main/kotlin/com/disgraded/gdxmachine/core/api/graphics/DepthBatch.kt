package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteDepthRenderer

class DepthBatch : DrawableBatch {

    private var depthRenderer = SpriteDepthRenderer()

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        depthRenderer.setProjectionMatrix(projectionMatrix)
        depthRenderer.begin()
        for (drawable in drawableList) {
            depthRenderer.draw(drawable)
        }
        return depthRenderer.end()
    }

    override fun dispose() {
        depthRenderer.dispose()
    }
}