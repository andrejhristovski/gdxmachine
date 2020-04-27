package com.disgraded.gdxmachine.framework.core.graphics

import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.graphics.utils.Transform2D
import kotlin.math.abs

abstract class Drawable2D: Transform2D(), Drawable {

    override var shader: Shader? = null
    val absolute = Transform2D()

    private var updated = false

    var visible: Boolean = true
    open var opacity = 1f
        set(value) {
            field = when {
                value > 1f -> 1f
                value < 0f -> 0f
                else -> value
            }
        }

    fun inherit(obj: Drawable2D) {
        shader = obj.shader
        visible = obj.visible
        opacity = obj.opacity
        set(obj)
    }

    fun isUpdated(): Boolean = updated

    fun update(transform2D: Transform2D? = null) {
        if (updated) return
        if (transform2D != null) {
            absolute.apply(this, transform2D)
        } else {
            absolute.set(this)
        }
        updated = true
    }

    override fun isExecutable(): Boolean = visible

    override fun getOrder(): Float = absolute.z
}