package com.disgraded.gdxmachine.core

import com.disgraded.gdxmachine.core.api.engine.EngineModule
import com.disgraded.gdxmachine.core.api.graphics.GraphicsModule
import com.disgraded.gdxmachine.core.api.input.InputModule
import com.disgraded.gdxmachine.core.api.physics.PhysicsModule
import com.disgraded.gdxmachine.core.api.resource.ResourceModule
import com.disgraded.gdxmachine.core.api.scene.SceneModule

/** Context object holds instances of APIs of the modules and is used as main interface to the engine features. */
object Context {
    lateinit var resources: ResourceModule.Api
    lateinit var engine: EngineModule.Api
    lateinit var scene: SceneModule.Api
    lateinit var graphics: GraphicsModule.Api
    lateinit var input: InputModule.Api
    lateinit var physics: PhysicsModule.Api
}