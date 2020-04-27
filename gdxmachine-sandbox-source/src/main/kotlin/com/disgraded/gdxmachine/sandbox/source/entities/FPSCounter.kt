package com.disgraded.gdxmachine.sandbox.source.entities

import com.disgraded.gdxmachine.framework.components.Render2DComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Rectangle
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

        transform.x = 510f
        transform.y = 270f
        transform.z = 10f

        text = Text()
        text.font = core.resources.get("engine").get("mono")
        text.color = Color.AMBER
        text.z = 0f
        render.add("main", text)
        text.setScale(.6f)

        bg = Rectangle()
        bg.width = 220f
        bg.height = 150f
        bg.opacity = .5f
        bg.color = Color.BLACK
        bg.z = -1f
        render.add("bg", bg)
    }

    override fun update(deltaTime: Float) {
        text.content = "FPS:${core.graphics.getFPS()}\n" +
                "GPU calls:${core.graphics.getGPUCalls()}\n" +
                "Heap:${core.app.memory.heap} MB\n" +
                "Native:${core.app.memory.native} MB"
    }

    override fun destroy() {

    }
}