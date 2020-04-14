package com.disgraded.gdxmachine.framework.components

import com.badlogic.ashley.core.Component
import com.disgraded.gdxmachine.framework.drawables.Sprite

class SpriteComponent(
        val layer: String = "default",
        var offsetX: Float = 0f,
        var offsetY: Float = 0f
) : Sprite(), Component