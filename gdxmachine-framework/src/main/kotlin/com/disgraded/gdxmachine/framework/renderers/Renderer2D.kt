package com.disgraded.gdxmachine.framework.renderers

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.disgraded.gdxmachine.framework.batches.ShapeBatch
import com.disgraded.gdxmachine.framework.batches.SpriteBatch
import com.disgraded.gdxmachine.framework.batches.TextBatch
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.BatchManager
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.Renderer
import com.disgraded.gdxmachine.framework.drawables.Shape
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.drawables.Text

class Renderer2D : Renderer {

    private val batchManager = BatchManager()
    private val worldRenderer = Box2DDebugRenderer()
    private val worldProjection = Matrix4()
    var world: World? = null

    private var gpuCalls = 0

    init {
        batchManager.addBatch(Sprite::class, SpriteBatch())
        batchManager.addBatch(Text::class, TextBatch())
        batchManager.addBatch(Shape::class, ShapeBatch())
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        gpuCalls = 0
        gpuCalls += batchManager.execute(drawableList, layer.camera.combined)
        if (world != null) {
            worldProjection.set(layer.camera.combined)
            worldProjection.scale(1f / Core.physics.units, 1f / Core.physics.units, 0f)
            worldRenderer.render(world, worldProjection)
            gpuCalls++
        }
        return gpuCalls
    }

    override fun dispose() {
        batchManager.dispose()
        worldRenderer.dispose()
    }
}