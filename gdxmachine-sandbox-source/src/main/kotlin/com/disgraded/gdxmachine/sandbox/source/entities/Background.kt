package com.disgraded.gdxmachine.sandbox.source.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.Body
import com.disgraded.gdxmachine.framework.components.Physics2DComponent
import com.disgraded.gdxmachine.framework.components.Graphics2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.drawables.Rectangle
import com.disgraded.gdxmachine.framework.drawables.Sprite

class Background: Entity() {

    private val transform: Transform2DComponent
    private val graphics: Graphics2DComponent

    private lateinit var background: Sprite
    private lateinit var body: Body

    init {
        add(Transform2DComponent())
        add(Graphics2DComponent())

        transform = get(Transform2DComponent::class)!!
        graphics = get(Graphics2DComponent::class)!!
    }

    override fun initialize() {

        background = Sprite()
        background.shader = core.graphics.getShader("sprite.sepia")
        background.setTexture(core.resources.get("test").get<Texture>("background"))
        background.z=-1f
        graphics.add("main", background)

        val wallTexture = core.resources.get("test").get<Texture>("wall")
        val wallTextureNormalMap = core.resources.get("test").get<Texture>("wall.normal")

        for (i in -50..50) {
            for(j in -50..50) {
                if (i == 0 && j == 0) continue
                val wall = Sprite()
                wall.visible = true
                wall.setTexture(wallTexture)
                wall.setTexture(wallTextureNormalMap, Sprite.NORMAL_TEXTURE)
                wall.opacity = 1f
                val tex = wall.getTexture()!!
                wall.setPosition(i * tex.regionWidth.toFloat(), j * tex.regionHeight.toFloat())
                graphics.add("wall.$i-$j", wall)
            }
        }

        val rect = Rectangle()
        rect.width = 100f
        rect.height = 40f
        graphics.add("rect", rect)
        rect.z = 1f
    }

    override fun update(deltaTime: Float) {

    }

    override fun destroy() {

    }
}