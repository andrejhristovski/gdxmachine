package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.core.api.resource.AssetPackage

class InitialAssets : AssetPackage("initial") {
    init {
        loadTexture("wall", "wall.png")
        loadTexture("wall_normal", "wall_normal.png")
        loadTexture("player", "texture.png")
    }
}