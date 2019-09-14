package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.renderer.SpriteBumpRenderer

class BumpBatch : DrawableBatch {

    private var bumpRenderer = SpriteBumpRenderer()

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        bumpRenderer.setProjectionMatrix(projectionMatrix)
        bumpRenderer.start()
        for (drawable in drawableList) {
            bumpRenderer.draw(drawable)
        }
        return bumpRenderer.finish()
    }

    override fun dispose() {
        bumpRenderer.dispose()
    }
}