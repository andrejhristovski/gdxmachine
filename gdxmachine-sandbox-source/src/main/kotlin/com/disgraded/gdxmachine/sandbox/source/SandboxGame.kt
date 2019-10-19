package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.framework.Api
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.graphics.Layer
import com.disgraded.gdxmachine.framework.resources.AssetPackage
import com.disgraded.gdxmachine.framework.resources.assets.ShaderData

class SandboxGame : EntryPoint {

    private lateinit var layer: Layer
    private val sprite = Sprite()

    override fun initialize() {
        val assetPackage = AssetPackage("default")
        assetPackage.load("sprite.vertex", "sprite.vertex.glsl", ShaderData::class, ShaderData.Parameters())
        assetPackage.load("sprite.fragment", "sprite.fragment.glsl", ShaderData::class, ShaderData.Parameters())
        assetPackage.load("texture", "wall.png", Texture::class)
        Api.resources.load(assetPackage, true)

        layer = Api.graphics.createLayer()
        layer.setRenderer(Renderer2D())
        sprite.texture = TextureRegion(assetPackage.get<Texture>("texture"))
    }

    override fun update(deltaTime: Float) {
        println(Api.graphics.getFPS())
        sprite.x += 100f * deltaTime
        layer.draw(sprite)
    }

    override fun destroy() {

    }
}