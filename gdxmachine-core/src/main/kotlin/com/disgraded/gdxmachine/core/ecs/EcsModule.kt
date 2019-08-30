package com.disgraded.gdxmachine.core.ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntityListener
import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.engine.Module
import com.disgraded.gdxmachine.core.engine.ModuleApi

class EcsModule(val game: Game) : Module {

    private val ecsApi = EcsApi(this)

    private val groups = hashMapOf<String, ArrayList<Entity>>()
    private val groupListeners = hashMapOf<String, ArrayList<EntityListener>>()

    override val api: ModuleApi
        get() = ecsApi


    lateinit var engine : Engine

    override fun load() {
        engine = Engine()
    }

    override fun unload() {
        for (entity in engine.entities) {
            ecsApi.remove(entity as Entity)
        }
        for (system in engine.systems) {
            ecsApi.removeSystem(system)
        }

        for (group in groups) {
            group.value.clear()
        }

        for (groupListener in groupListeners) {
            groupListener.value.clear()
        }
    }

    fun update() {
        engine.update(Gdx.graphics.deltaTime)
        for (entity in engine.entities) {
            (entity as Entity).update()
        }
    }

    fun reset() {
        unload()
        load()
    }

    fun addToGroup(entity: Entity) {
        if (entity.group !== null) {
            if (!groups.containsKey(entity.group)) {
                groups[entity.group] = arrayListOf()
                groupListeners[entity.group] = arrayListOf()
            } else {
                for (groupListener in groupListeners[entity.group]!!) {
                    groupListener.entityAdded(entity)
                }
            }
            groups[entity.group]!!.add(entity)
        }
    }

    fun removeFromGroup(entity: Entity) {
        if (entity.group !== null) {
            groups[entity.group]!!.remove(entity)
            for (groupListener in groupListeners[entity.group]!!) {
                groupListener.entityRemoved(entity)
            }
        }
    }

    fun getByGroup(group: String) : ArrayList<Entity> {
        if (groups.containsKey(group)) {
            val array = arrayListOf<Entity>()
            for (entity in groups[group]!!) {
                array.add(entity)
            }
            return array
        } else {
            throw RuntimeException("Group with the name \"$group\" doesn't exist!")
        }
    }

    fun addGroupListener(group: String, listener: EntityListener) {
        if (groupListeners.containsKey(group)) {
            groupListeners[group]!!.add(listener)
        } else {
            throw RuntimeException("Group with the name \"$group\" doesn't exist!")
        }
    }

    fun removeGroupListener(listener: EntityListener) {
        for (group in groupListeners) {
            group.value.remove(listener)
        }
    }

}