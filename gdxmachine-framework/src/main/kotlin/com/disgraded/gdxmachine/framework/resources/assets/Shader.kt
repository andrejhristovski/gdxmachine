package com.disgraded.gdxmachine.framework.resources.assets

import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class Shader(vertex: String, fragment: String) : ShaderProgram(vertex, fragment) {

    init {
        pedantic = false
    }

    data class Parameters(val vertexPath: String, val fragmentPath: String) : AssetLoaderParameters<Shader>() {
        val encoding = "UTF-8"
    }
}