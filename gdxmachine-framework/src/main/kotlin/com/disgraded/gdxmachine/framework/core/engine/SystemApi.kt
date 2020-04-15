package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Engine
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class SystemApi(private val engine: Engine) {

    private val systemMap = HashMap<KClass<System>, System>()

    fun add(systemClass: KClass<System>): System {
        if (systemMap.containsKey(systemClass)) {
            throw RuntimeException("The system of type $systemClass is already added")
        }
        val system = systemClass.createInstance()
        engine.addSystem(system)
        systemMap[systemClass] = system
        return system
    }

    fun remove(systemClass: KClass<System>) {
        if (systemMap.containsKey(systemClass)) {
            val system = systemMap[systemClass]!!
            engine.removeSystem(system)
            systemMap.remove(systemClass)
        } else {
            throw RuntimeException("The system of type $systemClass doesn't exist, can not be removed")
        }
    }

    fun get(systemClass: KClass<System>): System? {
        if (systemMap.containsKey(systemClass)) {
            return systemMap[systemClass]!!
        }
        return null
    }

    fun getAll(): ArrayList<System> {
        val list = ArrayList<System>()
        for (ecsSystem in engine.systems) {
            list.add(ecsSystem as System)
        }
        return list
    }

    fun removeAll() {
        for ((systemClass) in systemMap) {
            remove(systemClass)
        }
        systemMap.clear()
    }
}