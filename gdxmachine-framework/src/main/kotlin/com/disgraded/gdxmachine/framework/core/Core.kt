package com.disgraded.gdxmachine.framework.core

import com.badlogic.gdx.Audio
import com.badlogic.gdx.Net
import com.disgraded.gdxmachine.framework.core.application.ApplicationApi
import com.disgraded.gdxmachine.framework.core.engine.EngineApi
import com.disgraded.gdxmachine.framework.core.graphics.GraphicsApi
import com.disgraded.gdxmachine.framework.core.input.InputApi
import com.disgraded.gdxmachine.framework.core.physics.PhysicsApi
import com.disgraded.gdxmachine.framework.core.resources.ResourceApi

object Core {
    lateinit var input: InputApi
    lateinit var resources: ResourceApi
    lateinit var graphics: GraphicsApi
    lateinit var physics: PhysicsApi
    lateinit var network: Net
    lateinit var app: ApplicationApi
    lateinit var audio: Audio
    lateinit var engine: EngineApi
}