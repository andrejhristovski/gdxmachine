package com.disgraded.gdxmachine.sandbox.source.scenes

import com.badlogic.gdx.graphics.OrthographicCamera
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.renderers.Renderer2D
import com.disgraded.gdxmachine.framework.renderers.Renderer2DLighting
import com.disgraded.gdxmachine.framework.scenes.Scene
import com.disgraded.gdxmachine.framework.systems.Render2DSystem
import com.disgraded.gdxmachine.sandbox.source.entities.Background
import com.disgraded.gdxmachine.sandbox.source.entities.FPSCounter

class FirstScene : Scene() {

    private lateinit var layer: Layer
    private lateinit var camera: OrthographicCamera

    override fun prepare() {
        core.engine.systems.add(Render2DSystem::class)
        layer = core.graphics.getLayer()
        layer.setRenderer(Renderer2DLighting())
        camera = layer.camera as OrthographicCamera
        camera.position.x = 100f
    }

    override fun initialize() {
        core.engine.add(Background())
        core.engine.add(FPSCounter())
    }

    override fun update(deltaTime: Float) {
//        camera.position.x += 100f * deltaTime
    }

    override fun destroy() {

    }
}