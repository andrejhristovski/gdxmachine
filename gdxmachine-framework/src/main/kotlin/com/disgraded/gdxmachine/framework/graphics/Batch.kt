package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable

interface Batch: Disposable {

    fun begin()

    fun draw(drawable: Drawable)

    fun end(): Int
}