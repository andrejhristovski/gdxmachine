package com.disgraded.gdxmachine.framework.renderers

import com.badlogic.gdx.graphics.Pixmap
import com.disgraded.gdxmachine.framework.batches.ShapeBatch
import com.disgraded.gdxmachine.framework.batches.SpriteBatch
import com.disgraded.gdxmachine.framework.batches.TextBatch
import com.disgraded.gdxmachine.framework.core.graphics.BatchManager
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.Renderer
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Shape
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.drawables.Text
import com.disgraded.gdxmachine.framework.renderers.utils.LayerBuffer

class Renderer2DLighting: Renderer {

    private val diffuseBatchManager = BatchManager()
    private val bumpBatchManager = BatchManager()

    private var spriteBatch: SpriteBatch

    private val diffuseBuffer = LayerBuffer(Pixmap.Format.RGBA8888, false)
    private val bumpBuffer = LayerBuffer(Pixmap.Format.RGBA8888, false)

    init {
        diffuseBatchManager.addBatch(Sprite::class, SpriteBatch())
        diffuseBatchManager.addBatch(Text::class, TextBatch())
        diffuseBatchManager.addBatch(Shape::class, ShapeBatch())

        bumpBatchManager.addBatch(Sprite::class, SpriteBatch(SpriteBatch.Mode.BUMP))
        bumpBatchManager.addBatch(Text::class, TextBatch("text.bump", true))
        bumpBatchManager.addBatch(Shape::class, ShapeBatch(Color.NORMAL))

        bumpBuffer.color.set(Color.NORMAL)

        spriteBatch = SpriteBatch()
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        var gpuCalls = 0

        // render diffuse layer
        diffuseBuffer.draw(layer) {
            gpuCalls += diffuseBatchManager.execute(drawableList, layer.camera.projection)
        }

        // render bump layer
        bumpBuffer.draw(layer) {
            gpuCalls += bumpBatchManager.execute(drawableList, layer.camera.projection)
        }

        // final render
        spriteBatch.setProjectionMatrix(layer.camera.projection)
        spriteBatch.draw(diffuseBuffer.getSprite())
        gpuCalls += spriteBatch.end()
        return gpuCalls
    }

    override fun dispose() {

    }
}