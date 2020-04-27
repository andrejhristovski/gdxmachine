package com.disgraded.gdxmachine.framework.systems

import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.framework.components.*
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.engine.System
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.renderers.Renderer2D

class Render2DSystem: System(
        Family.all(
                Transform2DComponent::class.java,
                Render2DComponent::class.java
        ).get()
) {

    private lateinit var defaultLayer: Layer

    override fun initialize(entities: ArrayList<Entity>) {
        defaultLayer = core.graphics.createLayer()
        defaultLayer.setRenderer(Renderer2D())
    }

    override fun update(deltaTime: Float, entities: ArrayList<Entity>) {
        for (entity in entities) {
            processEntity(entity)
        }
    }

    private fun processEntity(entity: Entity) {
        val transform = entity.get(Transform2DComponent::class)!!
        val render = entity.get(Render2DComponent::class)!!

        render.getAll().forEach {
            it.update(transform)
            defaultLayer.draw(it)
        }
    }

    override fun destroy(entities: ArrayList<Entity>) {
        println("destroy")
    }
}