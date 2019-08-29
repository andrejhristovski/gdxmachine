package com.disgraded.gdxmachine.sandbox.source.asset

import com.disgraded.gdxmachine.core.resources.AssetPackage

class InitialAssets : AssetPackage("initial") {
    init {
        loadTexture("player","texture.png")
    }
}