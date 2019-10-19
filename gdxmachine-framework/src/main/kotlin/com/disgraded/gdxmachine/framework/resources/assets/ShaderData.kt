package com.disgraded.gdxmachine.framework.resources.assets

import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.graphics.glutils.ShaderProgram

data class ShaderData(val content: String) {
    class Parameters : AssetLoaderParameters<ShaderData>() {
        val encoding = "UTF-8"
    }
}