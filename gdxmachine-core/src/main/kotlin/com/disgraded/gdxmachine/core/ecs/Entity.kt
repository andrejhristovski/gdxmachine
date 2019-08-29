package com.disgraded.gdxmachine.core.ecs

import com.disgraded.gdxmachine.core.engine.Game
import com.badlogic.ashley.core.Entity as GdxEntity

abstract class Entity(val group: String?) : GdxEntity() {

    abstract fun initialize(game: Game)

    abstract fun update()

    abstract fun destroy()

}