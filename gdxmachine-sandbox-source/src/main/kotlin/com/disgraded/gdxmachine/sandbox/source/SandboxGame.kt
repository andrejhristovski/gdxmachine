package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.Context
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.graphics.Layer

class SandboxGame : EntryPoint {

    private lateinit var display: Layer
    private val sprite = Sprite()

    override fun initialize() {
        display = Context.graphics.createLayer()
        display.setRenderer(Renderer2D())
    }

    override fun update() {
        display.draw(sprite)
    }

    override fun destroy() {

    }
}