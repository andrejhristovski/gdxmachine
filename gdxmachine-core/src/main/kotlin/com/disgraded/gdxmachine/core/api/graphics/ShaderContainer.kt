package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class ShaderContainer {

    private val shaderMap = hashMapOf<String, ShaderProgram>()

    init {
        ShaderProgram.pedantic = false
    }

    fun get(type: Drawable.Type, effect: Drawable.Effect): ShaderProgram {
        val key = genKey(type, effect)
        if (shaderMap.containsKey(key)) {
            return shaderMap[key]!!
        }
        shaderMap[key] = compile(type.key, "${type.key}.${effect.key}")
        return shaderMap[key]!!
    }

    private fun genKey(type: Drawable.Type, effect: Drawable.Effect): String {
        return "${type.key}:${effect.key}"
    }

    private fun compile(vertex: String, fragment: String): ShaderProgram {
        val vertexSource = Gdx.files.classpath("shaders/vertex/$vertex.vertex.glsl").readString()
        val fragmentSource = Gdx.files.classpath("shaders/fragment/$fragment.fragment.glsl").readString()
        val shader = ShaderProgram(vertexSource, fragmentSource)
        require(shader.isCompiled) { "Error compiling shader: " + shader.log }
        return shader
    }

}
