package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite

interface Renderer : Disposable {

    var active: Boolean

    fun start()

    fun draw(drawable: Drawable)

    fun finish(): Int

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}