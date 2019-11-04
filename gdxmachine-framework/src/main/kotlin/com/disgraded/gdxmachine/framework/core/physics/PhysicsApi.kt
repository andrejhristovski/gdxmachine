package com.disgraded.gdxmachine.framework.core.physics

import com.badlogic.gdx.physics.box2d.World

class PhysicsApi(private val physicsModule: PhysicsModule) {

    var worldSpeed = 300f

    var positionIterations = 2

    var velocityIterations = 6

    fun createWorld(key: String = "default", doSleep: Boolean = true): World =
        physicsModule.createWorld(key, doSleep)

    fun getWorld(key: String = "default"): World = physicsModule.getWorld(key)

    fun destroyWorld(key: String = "default") = physicsModule.destroyWorld(key)

    fun existWorld(key: String = "default"): Boolean = physicsModule.existWorld(key)

    fun clear() = physicsModule.clear()

}