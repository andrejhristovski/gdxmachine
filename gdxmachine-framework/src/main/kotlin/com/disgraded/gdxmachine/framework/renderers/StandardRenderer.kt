package com.disgraded.gdxmachine.framework.renderers

import com.disgraded.gdxmachine.framework.batches.SpriteBatch
import com.disgraded.gdxmachine.framework.core.graphics.BatchManager
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.Renderer
import com.disgraded.gdxmachine.framework.drawables.Sprite

class StandardRenderer : Renderer {

    private val batchManager = BatchManager()

    init {
        batchManager.addBatch(Sprite::class, SpriteBatch())
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        return batchManager.execute(drawableList, layer.camera.combined)
    }

    override fun dispose() {

    }
}