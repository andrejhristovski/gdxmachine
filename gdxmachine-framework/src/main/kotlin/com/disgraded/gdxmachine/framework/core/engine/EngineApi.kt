package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family

class EngineApi(private val engine: Engine) {

    val systems = SystemApi(engine)

    val listeners = ListenerApi(engine)

    fun add(entity: Entity) {
        engine.addEntity(entity)
        entity.initialize()
    }

    fun remove(entity: Entity) {
        engine.removeEntity(entity)
        entity.destroy()
    }

    fun get(uid: String): Entity? {
        for (ecsEntity in engine.entities) {
            val entity = ecsEntity as Entity
            if (entity.uid == uid) {
                return entity
            }
        }
        return null
    }

    fun get(family: Family): ArrayList<Entity> {
        val list = ArrayList<Entity>()
        val entities = engine.getEntitiesFor(family)
        for (entity in entities) {
            list.add(entity as Entity)
        }
        return list
    }

    fun getAll(): ArrayList<Entity> {
        val list = ArrayList<Entity>()
        for (ecsEntity in engine.entities) {
            val entity = ecsEntity as Entity
            list.add(entity)
        }
        return list
    }

    fun removeAll() {
        for (entity in engine.entities) {
            (entity as Entity).destroy()
        }
        engine.removeAllEntities()
    }

    fun clear() {
        removeAll()
        systems.removeAll()
        listeners.removeAll()
    }

}