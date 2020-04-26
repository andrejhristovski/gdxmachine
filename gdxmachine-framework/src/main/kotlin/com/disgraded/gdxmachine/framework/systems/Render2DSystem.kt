package com.disgraded.gdxmachine.framework.systems

import com.badlogic.ashley.core.Family
import com.badlogic.gdx.math.Vector2
import com.disgraded.gdxmachine.framework.components.*
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.engine.System
import com.disgraded.gdxmachine.framework.drawables.Drawable2D
import com.disgraded.gdxmachine.framework.renderers.Renderer2D
import com.disgraded.gdxmachine.framework.utils.Transform2D

class Render2DSystem: System(
        Family.all(
                Transform2DComponent::class.java,
                Render2DComponent::class.java
        ).get()
) {

    override fun initialize(entities: ArrayList<Entity>) {
        val layer = core.graphics.createLayer()
        layer.setRenderer(Renderer2D())
    }

    override fun update(deltaTime: Float, entities: ArrayList<Entity>) {
        for (entity in entities) {
            processEntity(entity)
        }
    }

    private fun processEntity(entity: Entity) {
        val transform = entity.get(Transform2DComponent::class)!!
        val render = entity.get(Render2DComponent::class)!!
        render.update(transform)
        val drawables = render.getAll(true)
        for (drawable in drawables) {
            core.graphics.getLayer().draw(drawable)
        }

    }

    override fun destroy(entities: ArrayList<Entity>) {
        println("destroy")
    }
}