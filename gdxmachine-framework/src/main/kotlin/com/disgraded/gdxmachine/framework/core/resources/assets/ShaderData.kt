package com.disgraded.gdxmachine.framework.core.resources.assets

import com.badlogic.gdx.assets.AssetLoaderParameters

data class ShaderData(val content: String) {
    class Parameters : AssetLoaderParameters<ShaderData>() {
        val encoding = "UTF-8"
    }
}