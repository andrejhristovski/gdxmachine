package com.disgraded.gdxmachine.sandbox.source.asset

import com.disgraded.gdxmachine.core.api.resource.AssetPackage


class InitialAssets : AssetPackage("initial") {
    init {
        loadTexture("player","texture.png")
    }
}