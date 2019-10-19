package com.disgraded.gdxmachine.framework.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.disgraded.gdxmachine.framework.Module

class PhysicsModule : Module {

    val worldMap = hashMapOf<String, World>()

    override fun load() {

    }

    override fun unload() {

    }

    fun update() {

    }

    fun createWorld(key: String, doSleep: Boolean): World {
        if (worldMap.containsKey(key)) throw RuntimeException("") // TODO: message
        worldMap[key] = World(Vector2(0f, 0f), doSleep)
        return worldMap[key]!!
    }

    fun getWorld(key: String): World {
        if (!worldMap.containsKey(key)) throw RuntimeException("") // TODO: message
        return worldMap[key]!!
    }

    fun destroyWorld(key: String) {
        if (!worldMap.containsKey(key)) throw RuntimeException("") // TODO: message
        worldMap[key]!!.dispose()
        worldMap.remove(key)
    }

    fun existWorld(key: String): Boolean {
        return worldMap.containsKey(key)
    }

    fun clear() {
        for ((_, world) in worldMap) {
            world.dispose()
        }
        worldMap.clear()
    }
}