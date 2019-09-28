package com.disgraded.gdxmachine.core.api.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Disposable

class WorldContainer(private val worldSpeed: Float, private val velocityIterations: Int,
                     private val positionIterations: Int) : Disposable {

    val world = World(Vector2(0f, 0f), true)
    private var accumulator : Float = 0f

    fun update(deltaTime: Float) {
        val frameTime = Math.min(deltaTime, 0.25f)
        val worldTimestamp = 1 / 300f * worldSpeed
        accumulator += frameTime
        while(accumulator >= worldTimestamp) {
            world.step(worldTimestamp, velocityIterations, positionIterations)
            accumulator -= worldTimestamp
        }
    }

    override fun dispose() = world.dispose()
}