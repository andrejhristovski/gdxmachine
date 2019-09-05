package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable

interface Renderer<T : Drawable> {

    fun begin()

    fun draw(drawable: T)

    fun end()

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}