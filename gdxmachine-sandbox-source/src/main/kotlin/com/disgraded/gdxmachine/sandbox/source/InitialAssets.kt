package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.resource.AssetPackage
import com.disgraded.gdxmachine.core.api.resource.FontParams

class InitialAssets : AssetPackage("initial") {
    init {
        loadTexture("wall", "wall.png")
        loadTexture("wall_normal", "wall_normal.png")
        loadTexture("player", "texture.png")
        loadFont("text", "imperfecta.ttf", FontParams().apply {
            size = 88
            color = Color.AMBER
        })
    }
}