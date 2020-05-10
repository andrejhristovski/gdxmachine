package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Family

abstract class IntervalSystem(private val interval: Float, family: Family = Family.all().get(),
                              priority: Int = 0): System(family, priority) {

    private var accumulator = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while(accumulator >= interval) {
            super.update(accumulator)
            accumulator -= interval
        }
    }
}