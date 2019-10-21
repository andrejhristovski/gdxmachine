package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.EntryPoint
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.resources.AssetPackage
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class SandboxGame : EntryPoint {

    private lateinit var layer: Layer
    private val sprite = Sprite()

    override fun initialize(core: Core) {
        val assetPackage = AssetPackage("default")
        assetPackage.load("sprite.vertex", "sprite.vertex.glsl", ShaderData::class, ShaderData.Parameters())
        assetPackage.load("sprite.fragment", "sprite.fragment.glsl", ShaderData::class, ShaderData.Parameters())
        assetPackage.load("texture", "wall.png", Texture::class)
        core.resources.load(assetPackage, true)

        layer = core.graphics.createLayer()
        layer.setRenderer(Renderer2D(core))
        sprite.texture = TextureRegion(assetPackage.get<Texture>("texture"))

        val world = core.physics.createWorld()

    }

    override fun update(deltaTime: Float) {
        sprite.x += 100f * deltaTime
        layer.draw(sprite)
    }

    override fun destroy() {

    }
}