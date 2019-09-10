package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.core.api.resource.AssetPackage

class InitialAssets : AssetPackage("initial") {
    init {
        loadTexture("rock", "rock.png")
        loadTexture("rock_normal", "rock_normal.png")
        loadTexture("player", "texture.png")
    }
}