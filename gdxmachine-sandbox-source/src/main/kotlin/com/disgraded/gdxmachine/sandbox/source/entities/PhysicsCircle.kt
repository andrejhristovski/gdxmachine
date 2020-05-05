package com.disgraded.gdxmachine.sandbox.source.entities

import com.badlogic.gdx.physics.box2d.*
import com.disgraded.gdxmachine.framework.components.Physics2DComponent
import com.disgraded.gdxmachine.framework.components.Graphics2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Circle

class PhysicsCircle: Entity() {

    private val transform = Transform2DComponent()
    private val render = Graphics2DComponent()
    private val physics = Physics2DComponent()

    private lateinit var circle: Circle
    private lateinit var body: Body

    init {
        add(transform)
        add(render)
        add(physics)
    }

    override fun initialize() {

        circle = Circle()
        circle.radius = 100f
        circle.color = Color.AMBER
        render.add("main", circle)

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        val fixtureDef = FixtureDef()
        fixtureDef.shape = CircleShape()
        fixtureDef.shape.radius = 100f * core.physics.units
        body = core.physics.getWorld()!!.createBody(bodyDef)
        body.createFixture(fixtureDef)
        physics.body = body
        physics.body!!.applyForceToCenter(100f, 100f, true)
    }

    override fun update(deltaTime: Float) {

    }

    override fun destroy() {

    }
}