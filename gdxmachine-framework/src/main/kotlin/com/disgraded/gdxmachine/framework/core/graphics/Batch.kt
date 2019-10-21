package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable

interface Batch: Disposable {

    fun setProjectionMatrix(projectionMatrix: Matrix4)

    fun draw(drawable: Drawable)

    fun end(): Int
}