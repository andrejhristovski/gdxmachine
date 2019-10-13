package com.disgraded.gdxmachine.framework.resources.assets

import com.badlogic.gdx.assets.AssetLoaderParameters

data class PlainText(val text: String) {
    data class Parameters (val fileName: String) : AssetLoaderParameters<PlainText>() {
        val encoding = "UTF-8"
    }
}