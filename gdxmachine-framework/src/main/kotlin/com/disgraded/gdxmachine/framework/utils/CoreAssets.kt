package com.disgraded.gdxmachine.framework.utils

import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class CoreAssets: AssetPackage("core", ClasspathFileHandleResolver()) {

    init {
        load("sprite.vertex", "_core_assets/shaders/sprite.vertex.glsl", ShaderData::class)
        load("sprite.fragment", "_core_assets/shaders/fragment/sprite.fragment.glsl", ShaderData::class)

        load("sprite.mask.vertex", "_core_assets/shaders/sprite.mask.vertex.glsl", ShaderData::class)
        load("sprite.mask.fragment", "_core_assets/shaders/fragment/sprite.mask.fragment.glsl", ShaderData::class)

        load("no_image", "_core_assets/images/no_image.png", Texture::class)
        load("background", "_core_assets/images/gdx_bg.png", Texture::class)

        loadFont("sans", "_core_assets/fonts/sans.ttf")
        loadFont("mono", "_core_assets/fonts/mono.ttf")
        loadFont("display", "_core_assets/fonts/display.ttf")
        loadFont("handwriting", "_core_assets/fonts/handwriting.ttf")
    }

    override fun onComplete() {
        core.graphics.compileShader("sprite", get("sprite.vertex"), get("sprite.fragment"))
        core.graphics.compileShader("sprite.mask", get("sprite.mask.vertex"), get("sprite.mask.fragment"))
    }
}