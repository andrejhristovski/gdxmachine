package com.disgraded.gdxmachine.sandbox.source.scene

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.Drawable
import com.disgraded.gdxmachine.core.api.graphics.RenderContext
import com.disgraded.gdxmachine.core.api.scene.Scene
import com.disgraded.gdxmachine.sandbox.source.entity.TestEntity
import com.disgraded.gdxmachine.sandbox.source.system.TestSystem

class FirstScene : Scene {

    lateinit var context: Context
    lateinit var renderContext: RenderContext.RenderContextApi
    private val drawable = Drawable

    override fun initialize(context: Context) {
        this.context = context
        println("test 1")
        context.engine.add(TestEntity())
        context.engine.addSystem(TestSystem())

        renderContext = context.graphics.getContext()
        val texture = context.resources.get<Texture>("initial", "bg")
        drawable.textureRegion = TextureRegion(texture)
    }

    override fun update(deltaTime: Float) {
        renderContext.draw(drawable)
    }

    override fun destroy() {
        println("end test 1")
    }
}