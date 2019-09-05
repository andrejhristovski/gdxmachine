package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable

interface Renderer<T : Drawable> : Disposable {

    fun begin()

    fun draw(drawable: T)

    fun end()

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}