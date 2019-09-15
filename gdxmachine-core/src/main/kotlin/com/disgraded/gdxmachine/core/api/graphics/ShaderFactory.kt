package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class ShaderFactory private constructor() {

    companion object {
        private val shaderFactory = ShaderFactory()
        fun getInstance(): ShaderFactory = shaderFactory
    }

    private val shaderMap = hashMapOf<String, ShaderProgram>()

    fun get(vertexKey: String, fragmentKey: String): ShaderProgram {
        val key = "$vertexKey-$fragmentKey"
        if (!shaderMap.containsKey(key)) {
            val vertexSource = readInternalVertexShaderSource(vertexKey)
            val fragmentSource = readInternalFragmentShaderSource(fragmentKey)
            compile(key, vertexSource, fragmentSource)
        }
        return shaderMap[key]!!
    }

    private fun compile(key: String, vertexSource: String, fragmentSource: String) {
        val shader = ShaderProgram(vertexSource, fragmentSource)
        require(shader.isCompiled) { "Error compiling shader [$key]: " + shader.log }
        shaderMap[key] = shader
    }

    private fun readInternalVertexShaderSource(key: String): String {
        return Gdx.files.classpath("shader/$key.vertex.glsl").readString()
    }

    private fun readInternalFragmentShaderSource(key: String): String {
        return Gdx.files.classpath("shader/fragment/$key.fragment.glsl").readString()
    }

}
