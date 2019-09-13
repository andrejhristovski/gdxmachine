package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite

interface DrawableBatch: Disposable {

    fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int
}