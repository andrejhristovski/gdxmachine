package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable

interface DrawableHandler: Disposable {

    fun begin()

    fun draw(drawable: Drawable)

    fun end(): Int
}