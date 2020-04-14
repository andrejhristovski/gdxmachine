package com.disgraded.gdxmachine.framework.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.disgraded.gdxmachine.framework.components.SpriteComponent
import com.disgraded.gdxmachine.framework.components.TransformComponent
import com.disgraded.gdxmachine.framework.core.Core

class SpriteRenderSystem(priority: Int = 0) : IteratingSystem(
        Family.all(SpriteComponent::class.java, TransformComponent::class.java).get(),
        priority
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.getComponent(TransformComponent::class.java)
        val sprite = entity.getComponent(SpriteComponent::class.java)
        Core.graphics.getLayer(sprite.layer).draw(sprite)
    }
}