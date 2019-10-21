package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader

class Sprite: Drawable {

    override var shader: Shader? = null

    var scaleX = 1f
    var scaleY = 1f

    var x = 0f
    var y = 0f
    var z = 0f

    var anchorX = .5f
    var anchorY = .5f

    var angle = 0f

    var texture: TextureRegion? = null

    override fun getOrder(): Long = z.toLong()
}