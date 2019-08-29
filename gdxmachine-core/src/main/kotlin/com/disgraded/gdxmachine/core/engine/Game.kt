package com.disgraded.gdxmachine.core.engine

import com.disgraded.gdxmachine.core.ecs.EcsApi
import com.disgraded.gdxmachine.core.resources.ResourceApi
import com.disgraded.gdxmachine.core.scene.SceneApi

object Game {
    lateinit var ecs: EcsApi
    lateinit var resources: ResourceApi
    lateinit var scene: SceneApi
}