package com.disgraded.gdxmachine.framework

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class CorePackage: AssetPackage("engine") {

    init {
        load("sprite.vertex", "core/shaders/sprite.vertex.glsl", ShaderData::class)
        load("sprite.tint.fragment", "core/shaders/fragment/sprite.tint.fragment.glsl", ShaderData::class)
        load("sprite.bump.fragment", "core/shaders/fragment/sprite.bump.fragment.glsl", ShaderData::class)
        load("sprite.fill.fragment", "core/shaders/fragment/sprite.fill.fragment.glsl", ShaderData::class)
        load("sprite.greyscale.fragment", "core/shaders/fragment/sprite.greyscale.fragment.glsl", ShaderData::class)
        load("sprite.invert.fragment", "core/shaders/fragment/sprite.invert.fragment.glsl", ShaderData::class)
        load("sprite.sepia.fragment", "core/shaders/fragment/sprite.sepia.fragment.glsl", ShaderData::class)

        load("text.vertex", "core/shaders/text.vertex.glsl", ShaderData::class)
        load("text.tint.fragment", "core/shaders/fragment/text.tint.fragment.glsl", ShaderData::class)
        load("text.bump.fragment", "core/shaders/fragment/text.bump.fragment.glsl", ShaderData::class)

        load("no_image", "core/images/no_image.png", Texture::class)
        load("no_mask", "core/images/no_mask.png", Texture::class)
        load("no_normal", "core/images/no_normal.png", Texture::class)

        loadFont("sans", "core/fonts/sans.ttf")
        loadFont("mono", "core/fonts/mono.ttf")
        loadFont("display", "core/fonts/display.ttf")
        loadFont("handwriting", "core/fonts/handwriting.ttf")
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