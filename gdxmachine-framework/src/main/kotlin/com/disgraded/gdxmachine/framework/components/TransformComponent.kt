package com.disgraded.gdxmachine.framework.components

import com.badlogic.ashley.core.Component

class TransformComponent(
        var x: Float = 0f,
        var y: Float = 0f,
        var z: Float = 0f,
        var scaleX: Float = 1f,
        var scaleY: Float = 1f
) : Component