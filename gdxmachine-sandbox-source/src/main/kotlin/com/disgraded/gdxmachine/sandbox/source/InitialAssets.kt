package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.core.api.resource.AssetPackage

class InitialAssets : AssetPackage("initial") {
    init {
        loadTexture("rock", "bg.jpg")
        loadTexture("player", "texture.png")
    }
}