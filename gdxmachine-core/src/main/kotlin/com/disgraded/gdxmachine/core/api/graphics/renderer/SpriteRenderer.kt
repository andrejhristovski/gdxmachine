package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite

interface SpriteRenderer : Disposable {

    var active: Boolean

    fun begin()

    fun draw(sprite: Sprite)

    fun end(): Int

    fun setProjectionMatrix(projectionMatrix: Matrix4)
}