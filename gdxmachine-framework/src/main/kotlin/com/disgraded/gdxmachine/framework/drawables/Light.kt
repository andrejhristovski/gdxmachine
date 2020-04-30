package com.disgraded.gdxmachine.framework.drawables

import com.disgraded.gdxmachine.framework.core.Prototype
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Drawable2D
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import kotlin.reflect.KClass

class Light: Drawable2D(), Prototype<Light> {

    var color = Color.WHITE
    var intensity: Float = 1f

    override fun getType(): KClass<out Drawable> = Light::class

    override fun copy(): Light {
        val light = Light()
        light.inherit(this)
        return light
    }

    override fun inherit(obj: Light) {
        color = obj.color
        intensity = obj.intensity
        super.inherit(obj)
    }
}