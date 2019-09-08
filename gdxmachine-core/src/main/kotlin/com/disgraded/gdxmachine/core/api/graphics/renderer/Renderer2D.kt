package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D

interface Renderer2D : Disposable {

    val typeHandled: String
    var active: Boolean

    fun begin()

    fun draw(drawable: Drawable2D)

    fun end(): Int

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}