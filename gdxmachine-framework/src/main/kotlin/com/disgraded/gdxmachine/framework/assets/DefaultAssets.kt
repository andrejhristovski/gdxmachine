package com.disgraded.gdxmachine.framework.assets

import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class DefaultAssets: AssetPackage("_default", ClasspathFileHandleResolver()) {

    init {
        load("sprite.vertex", "_default/shaders/sprite.vertex.glsl", ShaderData::class)
        load("sprite.fragment", "_default/shaders/sprite.fragment.glsl", ShaderData::class)

        load("sprite.mask.vertex", "_default/shaders/sprite.mask.vertex.glsl", ShaderData::class)
        load("sprite.mask.fragment", "_default/shaders/sprite.mask.fragment.glsl", ShaderData::class)

        load("no_image", "_default/images/no_image.png", Texture::class)

        loadFont("sans", "_default/fonts/sans.ttf")
        loadFont("mono", "_default/fonts/mono.ttf")
        loadFont("display", "_default/fonts/display.ttf")
        loadFont("handwriting", "_default/fonts/handwriting.ttf")
    }

    override fun onComplete() {
        core.graphics.compileShader("sprite", get("sprite.vertex"), get("sprite.fragment"))
        core.graphics.compileShader("sprite.mask", get("sprite.mask.vertex"), get("sprite.mask.fragment"))
    }
}