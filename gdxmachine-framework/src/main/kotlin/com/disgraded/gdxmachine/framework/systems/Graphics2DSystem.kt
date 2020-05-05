package com.disgraded.gdxmachine.framework.systems

import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.framework.components.*
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.engine.System
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.renderers.Renderer2D

class Graphics2DSystem: System(
        Family.all(
                Transform2DComponent::class.java,
                Graphics2DComponent::class.java
        ).get()
) {

    private lateinit var defaultLayer: Layer
    private lateinit var layer: Layer

    private lateinit var transform: Transform2DComponent
    private lateinit var graphics: Graphics2DComponent

    override fun initialize(entities: ArrayList<Entity>) {
        if (core.graphics.getLayer() == null) {
            core.graphics.createLayer().setRenderer(Renderer2D())
        }
        defaultLayer = core.graphics.getLayer()!!
    }

    override fun update(deltaTime: Float, entities: ArrayList<Entity>) {
        for (entity in entities) processEntity(entity)
    }

    private fun processEntity(entity: Entity) {
        transform = entity.get(Transform2DComponent::class)!!
        graphics = entity.get(Graphics2DComponent::class)!!

        layer = if (graphics.layer != null) {
            graphics.layer!!
        } else {
            defaultLayer
        }

        graphics.getAll().forEach {
            it.update(transform)
            layer.draw(it)
        }
    }

    override fun destroy(entities: ArrayList<Entity>) {
        println("destroy")
    }
}