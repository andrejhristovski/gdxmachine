package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable

interface Renderer : Disposable {

    val typeHandled: String
    var active: Boolean

    fun begin()

    fun draw(drawable: Drawable)

    fun end(): Int

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}