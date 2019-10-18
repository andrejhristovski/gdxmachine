package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.Context
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.graphics.Display

class SandboxGame : EntryPoint {

    private lateinit var display: Display
    private val sprite = Sprite()
    private val sprite2 = Sprite2()

    override fun initialize() {
        display = Context.graphics.createViewport()
        display.renderer = Renderer2D()
    }

    override fun update() {
        display.draw(sprite)
    }

    override fun destroy() {

    }
}