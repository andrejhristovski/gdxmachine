package com.disgraded.gdxmachine.sandbox.source.scenes

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.renderers.Renderer2D
import com.disgraded.gdxmachine.framework.scenes.Scene
import com.disgraded.gdxmachine.framework.systems.Physics2DSystem
import com.disgraded.gdxmachine.framework.systems.Graphics2DSystem
import com.disgraded.gdxmachine.sandbox.source.entities.Background
import com.disgraded.gdxmachine.sandbox.source.entities.FPSCounter
import com.disgraded.gdxmachine.sandbox.source.entities.PhysicsCircle

class FirstScene : Scene() {

    private val renderer = Renderer2D()

    private lateinit var layer: Layer
    private lateinit var guiLayer: Layer
    private lateinit var camera: Camera

    private lateinit var world: World

    override fun prepare() {
        core.engine.systems.add(Physics2DSystem::class)
        core.engine.systems.add(Graphics2DSystem::class)

        layer = core.graphics.getLayer()!!
        guiLayer = core.graphics.createLayer("gui")
        guiLayer.setRenderer(renderer)

        camera = layer.camera

        world = core.physics.createWorld()
        world.gravity = Vector2(0f, 0f)
        renderer.world = world
    }

    override fun initialize() {
        core.engine.add(Background())
        core.engine.add(FPSCounter())
        core.engine.add(PhysicsCircle())
    }

    override fun update(deltaTime: Float) {
//        camera.position.x += 20f * deltaTime
    }

    override fun destroy() {

    }
}