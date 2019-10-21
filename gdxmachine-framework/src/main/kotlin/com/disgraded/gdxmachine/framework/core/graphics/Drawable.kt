package com.disgraded.gdxmachine.framework.core.graphics

import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader

interface Drawable {

    var shader: Shader?

    fun getOrder(): Long

}