package com.disgraded.gdxmachine.framework.systems

import com.badlogic.ashley.core.Family
import com.badlogic.gdx.physics.box2d.Body
import com.disgraded.gdxmachine.framework.components.Physics2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.engine.System

class Physics2DSystem: System(
        Family.all(
                Transform2DComponent::class.java,
                Physics2DComponent::class.java
        ).get()
) {

    private lateinit var transform: Transform2DComponent
    private lateinit var physics: Physics2DComponent

    override fun initialize(entities: ArrayList<Entity>) {
        
    }

    override fun update(deltaTime: Float, entities: ArrayList<Entity>) {
        for (entity in entities) processEntity(entity)
    }

    override fun destroy(entities: ArrayList<Entity>) {

    }

    private fun processEntity(entity: Entity) {
        transform = entity.get(Transform2DComponent::class)!!
        physics = entity.get(Physics2DComponent::class)!!
        if (physics.body != null) {
            transform.x = physics.body!!.position.x * (1f / core.physics.units)
            transform.y = physics.body!!.position.y * (1f / core.physics.units)
            transform.angle =physics.body!!.angle * 180 / Math.PI.toFloat()
        }
    }
}