package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite

interface SpriteBatch: Disposable {

    fun render(spriteList: ArrayList<Sprite>, projectionMatrix: Matrix4): Int
}