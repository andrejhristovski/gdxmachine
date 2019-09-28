package com.disgraded.gdxmachine.core.api.input

import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core

class InputModule : Core.Module {

    class Api(inputModule: InputModule) : Core.Api {

    }

    override val api: Core.Api = Api(this)

    override fun load(core: Core, config: Config) {

    }

    override fun update(deltaTime: Float) {

    }

    override fun unload() {

    }
}