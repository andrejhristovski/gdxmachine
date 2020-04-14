package com.disgraded.gdxmachine.framework.core.physics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.disgraded.gdxmachine.framework.core.Module

class PhysicsModule : Module {

    private val worldMap = hashMapOf<String, World>()
    private val worldAccumulatorMap = hashMapOf<String, Float>()

    val api = PhysicsApi(this)

    override fun load() {

    }

    override fun unload() {

    }

    fun update() {
        for ((key, world) in worldMap) {
            updateWorld(key, world)
        }
    }

    private fun updateWorld(key: String, world: World) {
        val deltaTime = Gdx.graphics.deltaTime
        var accumulator = worldAccumulatorMap[key]!!
        val frameTime = deltaTime.coerceAtMost(.25f)
        val worldTimestamp = 1 / 300f * api.worldSpeed
        accumulator += frameTime
        while (accumulator >= worldTimestamp) {
            world.step(worldTimestamp, api.velocityIterations, api.positionIterations)
            accumulator -= worldTimestamp
        }
        worldAccumulatorMap[key] = accumulator
    }

    fun createWorld(key: String, doSleep: Boolean): World {
        if (worldMap.containsKey(key)) throw RuntimeException("Physics world assigned as $key already exist!")
        worldMap[key] = World(Vector2(0f, 0f), doSleep)
        worldAccumulatorMap[key] = 0f
        return worldMap[key]!!
    }

    fun getWorld(key: String): World {
        if (!worldMap.containsKey(key)) throw RuntimeException("World [$key] doesn't exist!")
        return worldMap[key]!!
    }

    fun destroyWorld(key: String) {
        if (!worldMap.containsKey(key)) throw RuntimeException("World [$key] doesn't exist!")
        worldMap[key]!!.dispose()
        worldMap.remove(key)
        worldAccumulatorMap.remove(key)
    }

    fun existWorld(key: String): Boolean {
        return worldMap.containsKey(key)
    }

    fun clear() {
        for ((_, world) in worldMap) {
            world.dispose()
        }
        worldMap.clear()
        worldAccumulatorMap.clear()
    }
}