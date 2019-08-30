package com.disgraded.gdxmachine.core.api.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family

class ECSystem : Engine() {

    private val groupListeners = hashMapOf<String, ArrayList<EntityListener>>()
    private val registeredListeners = arrayListOf<EntityListener>()
    private val addedSystems = arrayListOf<EntitySystem>()

    fun add(entity: Entity) {
        super.addEntity(entity)
        if (groupListeners.containsKey(entity.group)) {
            for (listener in groupListeners[entity.group]!!) {
                listener.entityAdded(entity)
            }
        }
        entity.initialize()
    }

    fun remove(entity: Entity) {
        super.removeEntity(entity)
        if (groupListeners.containsKey(entity.group)) {
            for (listener in groupListeners[entity.group]!!) {
                listener.entityRemoved(entity)
            }
        }
        entity.destroy()
    }

    fun get(): ArrayList<Entity> {
        val list = arrayListOf<Entity>()
        for (entity in entities) {
            list.add(entity as Entity)
        }
        return list
    }

    fun get(family: Family) : ArrayList<Entity> {
        val list = arrayListOf<Entity>()
        for (entity in this.getEntitiesFor(family)) {
            list.add(entity as Entity)
        }
        return list
    }

    fun get(group: String) : ArrayList<Entity> {
        val list = arrayListOf<Entity>()
        for (entity in entities) {
            list.add(entity as Entity)
        }
        return list
    }

    override fun addSystem(system: EntitySystem) {
        super.addSystem(system)
        addedSystems.add(system)
    }

    override fun addEntityListener(listener: EntityListener) {
        super.addEntityListener(listener)
        registeredListeners.add(listener)
    }

    override fun addEntityListener(family: Family?, listener: EntityListener) {
        super.addEntityListener(family, listener)
        registeredListeners.add(listener)
    }

    fun addEntityListener(group: String, listener: EntityListener) {
        if (!groupListeners.containsKey(group)) {
            groupListeners[group] = arrayListOf()
        }
        groupListeners[group]!!.add(listener)
    }

    override fun removeEntityListener(listener: EntityListener) {
        super.removeEntityListener(listener)
        for (listenerList in groupListeners) {
            listenerList.value.remove(listener)
        }
    }

    fun clear() {
        for (entity in entities) {
            (entity as Entity).destroy()
        }
        removeAllEntities()
        for (system in addedSystems) removeSystem(system)
        for (listener in registeredListeners) super.removeEntityListener(listener)
        addedSystems.clear()
        registeredListeners.clear()
        groupListeners.clear()
    }
}