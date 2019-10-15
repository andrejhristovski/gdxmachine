package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.Context
import com.disgraded.gdxmachine.framework.EntryPoint
import com.disgraded.gdxmachine.framework.graphics.Viewport

class SandboxGame : EntryPoint {

    private lateinit var viewport: Viewport
    private val sprite = Sprite()
    private val sprite2 = Sprite2()

    override fun initialize() {
        viewport = Context.graphics.createViewport()
        viewport.renderer = Renderer2D()
    }

    override fun update() {
        viewport.draw(sprite)
    }

    override fun destroy() {

    }
}