package com.disgraded.gdxmachine.core.api.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.disgraded.gdxmachine.core.Context
import com.badlogic.ashley.core.EntitySystem as GdxEntitySystem

abstract class System(priority: Int = 0, private val interval: Int = 0) : GdxEntitySystem(priority) {

    private lateinit var family: Family
    protected lateinit var context: Context
    private var accumulator: Float = 0f

    abstract fun initialize() : Family

    abstract fun execute(entityList: ArrayList<Entity>, deltaTime: Float)

    abstract fun destroy()

    override fun update(deltaTime: Float) {
        if (interval == 0) {
            execute(context.engine.get(family), deltaTime)
        } else {
            accumulator += deltaTime * 1000
            while (accumulator >= interval) {
                accumulator -= interval
                execute(context.engine.get(family), deltaTime)
            }
        }
    }

    override fun addedToEngine(engine: Engine) {
        family = initialize()
        super.addedToEngine(engine)
    }

    override fun removedFromEngine(engine: Engine) {
        destroy()
        super.removedFromEngine(engine)
    }

    override fun getEngine(): ECSystem {
        return super.getEngine() as ECSystem
    }

    fun prepare(context: Context) {
        this.context = context
    }
}