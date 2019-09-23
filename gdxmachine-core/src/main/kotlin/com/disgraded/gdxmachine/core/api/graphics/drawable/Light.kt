package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.disgraded.gdxmachine.core.api.graphics.utils.Color

abstract class Light(val lightType: LightType): Drawable(Type.LIGHT) {

    var color = Color.WARM_WHITE

    var intensity = 1f
        set(value) { field = if (value > 1f) 1f else if(value < 0f) 0f else value }
    var radius = 0f
        set(value) { field = value % 360 }

    init {
        z = 100f
    }
}