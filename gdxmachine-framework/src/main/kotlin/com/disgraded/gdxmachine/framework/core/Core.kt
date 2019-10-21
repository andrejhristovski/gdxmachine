package com.disgraded.gdxmachine.framework.core

import com.disgraded.gdxmachine.framework.core.graphics.GraphicsApi
import com.disgraded.gdxmachine.framework.core.input.InputApi
import com.disgraded.gdxmachine.framework.core.physics.PhysicsApi
import com.disgraded.gdxmachine.framework.core.resources.ResourceApi

class Core {
    lateinit var input: InputApi
    lateinit var resources: ResourceApi
    lateinit var graphics: GraphicsApi
    lateinit var physics: PhysicsApi
}