package com.disgraded.gdxmachine.framework.extras

import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage
import com.disgraded.gdxmachine.framework.core.resources.FontParams
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class EngineAssets: AssetPackage("engine", ClasspathFileHandleResolver()) {

    init {
        load("sprite.vertex", "_core_assets/shaders/sprite.vertex.glsl", ShaderData::class)
        load("sprite.tint.fragment", "_core_assets/shaders/fragment/sprite.tint.fragment.glsl", ShaderData::class)
        load("sprite.bump.fragment", "_core_assets/shaders/fragment/sprite.bump.fragment.glsl", ShaderData::class)
        load("sprite.fill.fragment", "_core_assets/shaders/fragment/sprite.fill.fragment.glsl", ShaderData::class)
        load("sprite.greyscale.fragment", "_core_assets/shaders/fragment/sprite.greyscale.fragment.glsl", ShaderData::class)
        load("sprite.invert.fragment", "_core_assets/shaders/fragment/sprite.invert.fragment.glsl", ShaderData::class)
        load("sprite.sepia.fragment", "_core_assets/shaders/fragment/sprite.sepia.fragment.glsl", ShaderData::class)

        load("text.vertex", "_core_assets/shaders/text.vertex.glsl", ShaderData::class)
        load("text.tint.fragment", "_core_assets/shaders/fragment/text.tint.fragment.glsl", ShaderData::class)
        load("text.bump.fragment", "_core_assets/shaders/fragment/text.bump.fragment.glsl", ShaderData::class)

        load("no_image", "_core_assets/images/no_image.png", Texture::class)
        load("no_mask", "_core_assets/images/no_mask.png", Texture::class)
        load("no_normal", "_core_assets/images/no_normal.png", Texture::class)
        load("background", "_core_assets/images/gdx_bg.png", Texture::class)

        loadFont("sans", "_core_assets/fonts/sans.ttf")
        loadFont("mono", "_core_assets/fonts/mono.ttf")
        loadFont("display", "_core_assets/fonts/display.ttf")
        loadFont("handwriting", "_core_assets/fonts/handwriting.ttf")
    }

    override fun onComplete() {
        core.graphics.compileShader("sprite.tint", get("sprite.vertex"), get("sprite.tint.fragment"))
        core.graphics.compileShader("sprite.bump", get("sprite.vertex"), get("sprite.bump.fragment"))
        core.graphics.compileShader("sprite.fill", get("sprite.vertex"), get("sprite.fill.fragment"))
        core.graphics.compileShader("sprite.greyscale", get("sprite.vertex"), get("sprite.greyscale.fragment"))
        core.graphics.compileShader("sprite.invert", get("sprite.vertex"), get("sprite.invert.fragment"))
        core.graphics.compileShader("sprite.sepia", get("sprite.vertex"), get("sprite.sepia.fragment"))

        core.graphics.compileShader("text.tint", get("text.vertex"), get("text.tint.fragment"))
        core.graphics.compileShader("text.bump", get("text.vertex"), get("text.bump.fragment"))
    }
}