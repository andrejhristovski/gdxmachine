package com.disgraded.gdxmachine.framework.core.physics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.disgraded.gdxmachine.framework.core.Module

class PhysicsModule : Module {

    private val worldList = arrayListOf<World>()
    private val worldMap = hashMapOf<String, World>()

    private var accumulator = 0f

    val api = PhysicsApi(this)

    override fun load() {

    }

    override fun unload() {

    }

    fun update() {
        worldList.forEach { updateWorld(it) }
    }

    private fun updateWorld(world: World) {
        val deltaTime = Gdx.graphics.deltaTime
        val frameTime = deltaTime.coerceAtMost(.25f)
        val worldTimestamp = 1 / 300f * api.worldSpeed
        accumulator += frameTime
        while (accumulator >= worldTimestamp) {
            world.step(worldTimestamp, api.velocityIterations, api.positionIterations)
            accumulator -= worldTimestamp
        }
    }

    fun createWorld(key: String, doSleep: Boolean): World {
        if (worldMap.containsKey(key)) throw RuntimeException("Physics world assigned as $key already exist!")
        val world = World(Vector2(0f, 0f), doSleep)
        worldMap[key] = world
        worldList.add(world)
        return worldMap[key]!!
    }

    fun getWorld(key: String): World {
        if (!worldMap.containsKey(key)) throw RuntimeException("World [$key] doesn't exist!")
        return worldMap[key]!!
    }

    fun destroyWorld(key: String) {
        if (!worldMap.containsKey(key)) throw RuntimeException("World [$key] doesn't exist!")
        worldMap[key]!!.dispose()
        worldList.remove(worldMap[key]!!)
        worldMap.remove(key)
    }

    fun existWorld(key: String): Boolean = worldMap.containsKey(key)

    fun clear() {
        worldList.forEach { it.dispose() }
        worldMap.clear()
    }
}