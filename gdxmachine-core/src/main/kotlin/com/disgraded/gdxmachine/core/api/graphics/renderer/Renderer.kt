package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D

interface Renderer<T : Drawable2D> : Disposable {

    fun begin()

    fun draw(drawable: T)

    fun end(): Int

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}