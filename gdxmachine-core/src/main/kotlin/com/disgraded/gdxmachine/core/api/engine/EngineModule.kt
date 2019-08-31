package com.disgraded.gdxmachine.core.api.engine

import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.core.Core

class EngineModule : Core.Module {

    class EngineApi(private val engineModule: EngineModule) : Core.Api {
        fun add(entity: Entity) = engineModule.ecs.add(entity)

        fun get(): ArrayList<Entity> = engineModule.ecs.get()

        fun get(family: Family): ArrayList<Entity> = engineModule.ecs.get(family)

        fun get(group: String): ArrayList<Entity> = engineModule.ecs.get(group)

        fun remove(entity: Entity) = engineModule.ecs.remove(entity)

        fun removeAll() = engineModule.ecs.removeAllEntities()

        fun addSystem(system: System) = engineModule.ecs.addSystem(system)

        fun removeSystem(system: System) = engineModule.ecs.removeSystem(system)

        fun addListener(listener: EntityListener) = engineModule.ecs.addEntityListener(listener)

        fun addListener(family: Family, listener: EntityListener) =
                engineModule.ecs.addEntityListener(family, listener)

        fun addListener(group: String, listener: EntityListener) =
                engineModule.ecs.addEntityListener(group, listener)

        fun removeListener(listener: EntityListener) =
                engineModule.ecs.removeEntityListener(listener)
    }

    override val api: Core.Api = EngineApi(this)

    lateinit var ecs: ECSystem

    override fun load(core: Core) {
        ecs = ECSystem(core.context)
    }

    override fun update(deltaTime: Float) {
        ecs.update(deltaTime)
    }

    override fun unload() {
        ecs.clear()
    }
}