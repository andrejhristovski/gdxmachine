package com.disgraded.gdxmachine.framework.core.graphics

import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import kotlin.reflect.KClass

interface Drawable {

    var shader: Shader?

    fun getOrder(): Long

    fun getType(): KClass<out Drawable>

    fun isExecutable(): Boolean

}