package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.framework.core.Core
import java.util.*
import kotlin.collections.ArrayList

abstract class System(
        val family: Family = Family.all().get(),
        priority: Int = 0
): EntitySystem(priority) {

    val uid = UUID.randomUUID().toString()

    protected val core = Core
    private var entities = ArrayList<Entity>()

    protected abstract fun initialize(entities: ArrayList<Entity>)

    protected abstract fun update(deltaTime: Float, entities: ArrayList<Entity>)

    protected abstract fun destroy(entities: ArrayList<Entity>)

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        update(deltaTime, core.engine.get(family))
    }

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        initialize(core.engine.get(family))
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        destroy(core.engine.get(family))
    }
}