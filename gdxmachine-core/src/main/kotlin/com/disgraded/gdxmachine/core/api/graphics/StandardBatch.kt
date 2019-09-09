package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.*

class StandardBatch: SpriteBatch {

    private val rendererMap = hashMapOf<String, SpriteRenderer>()
    private var currentRenderer: SpriteRenderer? = null
    private var currentRendererType: String? = null

    private val spriteStandardRenderer = SpriteStandardRenderer()

    init {
        rendererMap["standard_sprite"] = SpriteStandardRenderer()
//        rendererMap["masked_sprite"] = SpriteMaskRenderer()
    }

    override fun render(spriteList: ArrayList<Sprite>, projectionMatrix: Matrix4): Int {
        var gpuCalls = 0
        spriteStandardRenderer.setProjectionMatrix(projectionMatrix)
        spriteStandardRenderer.begin()
        for (sprite in spriteList) {
            spriteStandardRenderer.draw(sprite)
        }
        gpuCalls += spriteStandardRenderer.end()
        return gpuCalls
    }

    private fun validateRenderer(sprite: Sprite) {
        val rendererType =  if (sprite.getMask() !== null) "standard_sprite" else "masked_sprite"
    }

    override fun dispose() {
        for(renderer in rendererMap) {
            renderer.value.dispose()
        }
        rendererMap.clear()
    }
}