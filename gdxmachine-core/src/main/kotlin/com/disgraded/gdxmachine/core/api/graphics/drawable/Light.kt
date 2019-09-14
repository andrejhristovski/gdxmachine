package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.math.Vector3
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class Light: Drawable(Type.LIGHT) {
    var color = Color.WARM_WHITE
    val falloff = Vector3(.4f, 3f, 20f)

    init {
        z = .1f
    }
}