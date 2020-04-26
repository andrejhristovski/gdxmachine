package com.disgraded.gdxmachine.sandbox.source.entities

import com.badlogic.gdx.utils.Align
import com.disgraded.gdxmachine.framework.components.Render2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Rectangle
import com.disgraded.gdxmachine.framework.drawables.Shape
import com.disgraded.gdxmachine.framework.drawables.Text

class FPSCounter: Entity() {

    private lateinit var text: Text
    private lateinit var bg: Rectangle

    init {
        add(Transform2DComponent())
        add(Render2DComponent())
    }

    override fun initialize() {
        val transform = get(Transform2DComponent::class)!!
        val render = get(Render2DComponent::class)!!

        transform.x = 530f
        transform.y = 300f
        transform.z = 10f

        text = Text()
        text.font = core.resources.get("engine").get("display")
        text.color = Color.AMBER
        render.add("main", text)

        bg = Rectangle()
        bg.width = 180f
        bg.height = 100f
        bg.opacity = .5f
        bg.color = Color.BLACK
        render.add("bg", bg)


    }

    override fun update(deltaTime: Float) {
        text.content = "FPS:${core.graphics.getFPS()}\nGPU CALLS:${core.graphics.getGPUCalls()}"
    }

    override fun destroy() {

    }
}