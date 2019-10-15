package com.disgraded.gdxmachine.framework

import com.disgraded.gdxmachine.framework.graphics.GraphicsApi
import com.disgraded.gdxmachine.framework.input.InputApi
import com.disgraded.gdxmachine.framework.resources.ResourceApi

object Context {
    lateinit var input: InputApi
    lateinit var resources: ResourceApi
    lateinit var graphics: GraphicsApi
}