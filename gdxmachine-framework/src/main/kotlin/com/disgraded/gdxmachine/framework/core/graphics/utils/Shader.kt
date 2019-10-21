package com.disgraded.gdxmachine.framework.core.graphics.utils

import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class Shader(vertex: ShaderData, fragment: ShaderData): ShaderProgram(vertex.content, fragment.content) {
    init {
        pedantic = false
    }
}