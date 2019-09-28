package com.disgraded.gdxmachine.core.api.input

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core

class InputModule : Core.Module {

    class Api(inputModule: InputModule) : Core.Api {

        val onTouchDown = Signal<Int>()
        val onTouchUp = Signal<Int>()
        val onTouchDragged = Signal<Int>()

        val onMouseClick = Signal<String>()
        val onMouseScroll = Signal<Int>()
        val onMouseMove = Signal<Vector2>()

        val onKeyTyped = Signal<String>()
        val onKeyDown = Signal<String>()
        val onKeyUp = Signal<String>()
    }

    override val api: Core.Api = Api(this)

    private lateinit var inputProcessor : InputProcessor

    override fun load(core: Core, config: Config) {
        inputProcessor  = InputProcessor(api as Api)
    }

    override fun update(deltaTime: Float) {
        inputProcessor.update()
    }

    override fun unload() {

    }
}