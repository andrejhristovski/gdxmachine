package com.disgraded.gdxmachine.framework.extra

import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class DefaultAssets: AssetPackage("_default", ClasspathFileHandleResolver()) {

    init {
        load("sprite.vertex", "_default/shaders/sprite.vertex.glsl", ShaderData::class)
        load("sprite.fragment", "_default/shaders/sprite.fragment.glsl", ShaderData::class)

        load("no_image", "_default/images/no_image.png", Texture::class)
    }

    override fun onComplete() {
        core.graphics.compileShader("sprite", get("sprite.vertex"), get("sprite.fragment"))
    }
}