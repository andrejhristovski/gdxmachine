package com.disgraded.gdxmachine.core.api.graphics.batch

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable

interface DrawableBatch: Disposable {

    fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int
}