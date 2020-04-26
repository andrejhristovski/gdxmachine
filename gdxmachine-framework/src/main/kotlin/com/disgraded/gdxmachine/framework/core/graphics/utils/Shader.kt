package com.disgraded.gdxmachine.framework.core.graphics.utils

import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class Shader(vertex: ShaderData, fragment: ShaderData): ShaderProgram(vertex.content, fragment.content) {

    companion object {
        fun POSITION(index: Short = 0) = "a_position$index"
        fun NORMAL(index: Short = 0) = "a_normal$index"
        fun COLOR(index: Short = 0) = "a_color$index"
        fun TEXCOORD(index: Short = 0) = "a_texCoord$index"
        fun TANGENT(index: Short = 0) = "a_tangent$index"
        fun BINORMAL(index: Short = 0) = "a_binormal$index"
        fun BONEWEIGHT(index: Short = 0) = "a_boneWeight$index"
        fun GENERAL(name: String, index: Short = 0) = "a_general_$name$index"
    }

    init {
        pedantic = false
    }
}