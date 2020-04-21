package com.disgraded.gdxmachine.framework.drawables

import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.utils.Transform2D

abstract class Drawable2D: Transform2D(), Drawable {

    override var shader: Shader? = null

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
        x = obj.x
        y = obj.y
        z = obj.z
        scaleX = obj.scaleX
        scaleY = obj.scaleY
        anchorX = obj.anchorX
        anchorY = obj.anchorY
        angle = obj.angle
    }

    override fun getOrder(): Long = z.toLong()

    override fun isExecutable(): Boolean = visible
}