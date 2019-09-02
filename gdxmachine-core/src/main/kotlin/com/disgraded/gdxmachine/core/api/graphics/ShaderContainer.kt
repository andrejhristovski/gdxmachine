package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class ShaderContainer {

    private val batch = SpriteBatch(1)

    fun get(type: String): ShaderProgram {
        return batch.shader
    }

}
