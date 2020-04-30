package com.disgraded.gdxmachine.sandbox.source.entities

import com.badlogic.gdx.graphics.Texture
import com.disgraded.gdxmachine.framework.components.Render2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Light
import com.disgraded.gdxmachine.framework.drawables.Rectangle
import com.disgraded.gdxmachine.framework.drawables.Sprite

class Background: Entity() {

    private lateinit var background: Sprite
    private lateinit var light: Light

    init {
        add(Transform2DComponent())
        add(Render2DComponent())
    }

    override fun initialize() {
        val render = get(Render2DComponent::class)!!

        background = Sprite()
        background.shader = core.graphics.getShader("sprite.sepia")
        background.setTexture(core.resources.get("test").get<Texture>("background"))
        background.z=-1f
        render.add("main", background)

        val wallTexture = core.resources.get("test").get<Texture>("wall")
        val wallTextureNormalMap = core.resources.get("test").get<Texture>("wall.normal")

        for (i in -5..5) {
            for(j in -5..5) {
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

        light = Light()
        light.color = Color.WARM_WHITE
        light.z = 0f
        light.x = 100f
        light.y = 100f
        render.add("first_light", light)

    }

    override fun update(deltaTime: Float) {
//        light.z += 1f * deltaTime
//        light.x += 10f * deltaTime
//        light.y += 10f * deltaTime
    }

    override fun destroy() {

    }
}