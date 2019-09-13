package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.renderer.normal.SpriteNormalRenderer

class NormalBatch : DrawableBatch {

    private var normalRenderer = SpriteNormalRenderer()

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        normalRenderer.setProjectionMatrix(projectionMatrix)
        normalRenderer.begin()
        for (drawable in drawableList) {
            normalRenderer.draw(drawable)
        }
        return normalRenderer.end()
    }

    override fun dispose() {
        normalRenderer.dispose()
    }
}