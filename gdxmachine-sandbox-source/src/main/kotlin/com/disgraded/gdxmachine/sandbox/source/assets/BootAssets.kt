package com.disgraded.gdxmachine.sandbox.source.assets

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage

class BootAssets: AssetPackage("boot") {

    init {
        load("sign", "sign.png", Texture::class)
        load("wall", "wall.png", Texture::class)
        load("wall.normal", "wall_normal.png", Texture::class)
        load("anim", "anim.png", Texture::class)
        load("background", "bg.jpg", Texture::class)
    }
}