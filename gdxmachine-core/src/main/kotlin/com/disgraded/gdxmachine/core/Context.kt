package com.disgraded.gdxmachine.core

import com.disgraded.gdxmachine.core.api.engine.EngineModule
import com.disgraded.gdxmachine.core.api.graphics.GraphicsModule
import com.disgraded.gdxmachine.core.api.resource.ResourceModule
import com.disgraded.gdxmachine.core.api.scene.SceneModule

object Context {
    lateinit var resources: ResourceModule.ResourceApi
    lateinit var engine: EngineModule.EngineApi
    lateinit var scene: SceneModule.SceneApi
    lateinit var graphics: GraphicsModule.GraphicsApi
}