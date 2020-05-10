package com.disgraded.gdxmachine.framework.core.physics

import com.badlogic.gdx.physics.box2d.World

class PhysicsApi(private val physicsModule: PhysicsModule) {

    val units = 1f / 32f

    var positionIterations = 2

    var velocityIterations = 6

    fun createWorld(key: String = "default", doSleep: Boolean = true): World =
        physicsModule.createWorld(key, doSleep)

    fun getWorld(key: String = "default"): World? = physicsModule.getWorld(key)

    fun getWorldList(): ArrayList<World> = physicsModule.getWorldList()

    fun destroyWorld(key: String = "default") = physicsModule.destroyWorld(key)

    fun clear() = physicsModule.clear()

}