package com.disgraded.gdxmachine.framework.graphics.utils

import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.disgraded.gdxmachine.framework.resources.assets.ShaderData

class Shader(vertex: ShaderData, fragment: ShaderData): ShaderProgram(vertex.content, fragment.content) {
    init {
        pedantic = false
    }
}