package com.disgraded.gdxmachine.framework.core.graphics

import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import kotlin.reflect.KClass

interface Drawable: Comparable<Drawable> {

    var shader: Shader?

    fun getType(): KClass<out Drawable>

    fun isExecutable(): Boolean

    override fun compareTo(other: Drawable): Int = getOrder().compareTo(other.getOrder())

    fun getOrder(): Float
}