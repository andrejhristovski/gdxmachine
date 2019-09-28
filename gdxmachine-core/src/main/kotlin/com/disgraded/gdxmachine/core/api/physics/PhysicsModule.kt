package com.disgraded.gdxmachine.core.api.physics

import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.World
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core

class PhysicsModule : Core.Module {

    class Api(private val physicsModule: PhysicsModule) : Core.Api {
        fun createWorld(name: String, worldSpeed: Float = 1f, velocityIterations: Int = 6, positionIterations: Int = 2)
                : World = physicsModule.createWorld(name, worldSpeed, velocityIterations, positionIterations)

        fun getWorld(name: String): World = physicsModule.getWorld(name)

        fun deleteWorld(name: String) = physicsModule.deleteWorld(name)
    }

    private val worldContainers = hashMapOf<String, WorldContainer>()

    override val api: Core.Api = Api(this)

    override fun load(core: Core, config: Config) {
        Box2D.init()
    }

    override fun update(deltaTime: Float) {
        worldContainers.forEach {
            it.value.update(deltaTime)
        }
    }

    override fun unload() = clear()

    fun clear() {
        for (worldContainer in worldContainers) {
            worldContainer.value.dispose()
        }
        worldContainers.clear()
    }

    private fun createWorld(name: String, worldSpeed: Float, velocityIterations: Int, positionIterations: Int): World {
        if (worldContainers.containsKey(name)) throw RuntimeException("World with the name \"$name\" it's already created!")
        worldContainers[name] = WorldContainer(worldSpeed, velocityIterations, positionIterations)
        return worldContainers[name]!!.world
    }

    private fun getWorld(name: String): World {
        if (worldContainers.containsKey(name)) {
            return worldContainers[name]!!.world
        }
        throw RuntimeException("World with the name \"$name\" doesn't exist!")
    }

    private fun deleteWorld(name: String) {
        if (!worldContainers.containsKey(name)) throw RuntimeException("World with the name \"$name\" doesn't exist!")
        worldContainers[name]!!.dispose()
        worldContainers.remove(name)
    }
}