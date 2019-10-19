package com.disgraded.gdxmachine.framework.physics

import com.badlogic.gdx.physics.box2d.World

data class WorldSettings(var velocityIterations: Int = 6, var positionIterations: Int = 2,
                         var speed: Float = 300f) {

    fun update(world: World) {

    }
}