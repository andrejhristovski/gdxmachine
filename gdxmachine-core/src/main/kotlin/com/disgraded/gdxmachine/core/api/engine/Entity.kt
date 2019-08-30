package com.disgraded.gdxmachine.core.api.engine

import com.badlogic.ashley.core.Entity as GdxEntity

abstract class Entity(val group: String? = null) : GdxEntity() {

    abstract fun initialize()

    abstract fun update()

    abstract fun destroy()

}