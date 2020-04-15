package com.disgraded.gdxmachine.framework.core.engine

import java.util.*
import kotlin.reflect.KClass
import com.badlogic.ashley.core.Entity as EcsEntity

abstract class Entity: EcsEntity() {

    val uid = UUID.randomUUID().toString()

    abstract fun initialize()

    abstract fun update(deltaTime: Float)

    abstract fun destroy()

    fun add(component: Component): Entity {
        super.add(component)
        return this
    }

    fun remove(componentClass: KClass<out Component>): Entity {
        super.remove(componentClass.java) as Component
        return this
    }

    fun get(componentClass: KClass<out Component>): Component {
        return super.getComponent(componentClass.java) as Component
    }

}