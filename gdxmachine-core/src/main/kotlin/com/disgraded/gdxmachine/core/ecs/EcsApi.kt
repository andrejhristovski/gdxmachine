package com.disgraded.gdxmachine.core.ecs

import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.core.engine.module.ModuleApi
import kotlin.reflect.KClass

class EcsApi(private val ecsModule: EcsModule) : ModuleApi {
    fun add(entity: Entity) {
        ecsModule.engine.addEntity(entity)
        entity.initialize(ecsModule.game)
    }

    fun remove(entity: Entity) {
        ecsModule.engine.removeEntity(entity)
        entity.destroy()
    }

    fun get() : ArrayList<Entity> {
        val list = arrayListOf<Entity>()
        for (entity in ecsModule.engine.entities) {
            list.add(entity as Entity)
        }
        return list
    }

    fun get(family: Family) : ArrayList<Entity> {
        val list = arrayListOf<Entity>()
        for (entity in ecsModule.engine.getEntitiesFor(family)) {
            list.add(entity as Entity)
        }
        return list
    }

    fun removeAll() {
        ecsModule.engine.removeAllEntities()
    }

    fun addSystem(system: EntitySystem) {
        ecsModule.engine.addSystem(system)
    }

    fun getSystem(systemClass: KClass<EntitySystem>) : EntitySystem {
        return ecsModule.engine.getSystem(systemClass.java)
    }

    fun removeSystem(system: EntitySystem) {
        ecsModule.engine.removeSystem(system)
    }

    fun addListener(listener: EntityListener) {
        ecsModule.engine.addEntityListener(listener)
    }

    fun addListener(family: Family, listener: EntityListener) {
        ecsModule.engine.addEntityListener(family, listener)
    }

    fun removeListener(listener: EntityListener) {
        ecsModule.engine.removeEntityListener(listener)
    }
}