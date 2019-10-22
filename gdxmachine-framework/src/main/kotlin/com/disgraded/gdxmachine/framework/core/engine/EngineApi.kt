package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Engine

class EngineApi: Engine() {

    fun clear() {
        removeAllEntities()
        for (system in systems) removeSystem(system)
    }
}