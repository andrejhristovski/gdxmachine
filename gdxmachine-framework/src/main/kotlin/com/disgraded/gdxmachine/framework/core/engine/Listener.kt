package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Family
import java.util.*
import com.badlogic.ashley.core.Entity as EcsEntity
import com.badlogic.ashley.core.EntityListener as EcsListener

abstract class Listener(
        val family: Family = Family.all().get(),
        val priority: Int = 0
        ): EcsListener {

    val uid = UUID.randomUUID().toString()

    abstract fun added(entity: Entity)

    abstract fun removed(entity: Entity)

    override fun entityRemoved(entity: EcsEntity?) {
        added(entity as Entity)
    }

    override fun entityAdded(entity: EcsEntity?) {
        added(entity as Entity)
    }
}