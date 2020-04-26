package com.disgraded.gdxmachine.sandbox.source.entities

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.components.Render2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Rectangle
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.utils.Corner

class Background: Entity() {

    private lateinit var background: Sprite

    init {
        add(Transform2DComponent())
        add(Render2DComponent())
    }

    override fun initialize() {
        val transform = get(Transform2DComponent::class)!!
        val render = get(Render2DComponent::class)!!

        background = Sprite()
        background.shader = core.graphics.getShader("sprite.sepia")
        background.setTexture(core.resources.get("test").get<Texture>("background"))
        background.z=-1f
        render.add("main", background)

        val wallTexture = core.resources.get("test").get<Texture>("wall")
        val wallTextureNormalMap = core.resources.get("test").get<Texture>("wall.normal")

        for (i in -2..2) {
            for(j in -2..2) {
                if (i == 0 && j == 0) continue
                val wall = Sprite()
                wall.setTexture(wallTexture)
                wall.setTexture(wallTextureNormalMap, Sprite.NORMAL_TEXTURE)
                wall.opacity = 1f
                val tex = wall.getTexture()!!
                wall.setPosition(i * tex.regionWidth.toFloat(), j * tex.regionHeight.toFloat())
                render.add("wall.$i-$j", wall)
            }
        }

        val rect = Rectangle()
        rect.width = 100f
        rect.height = 40f
        render.add("rect", rect)
        rect.z = 1f
    }

    override fun update(deltaTime: Float) {

    }

    override fun destroy() {

    }
}