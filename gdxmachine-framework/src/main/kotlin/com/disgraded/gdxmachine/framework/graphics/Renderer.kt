package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable

interface Renderer: Disposable {

    val gpuCalls: Int

    fun render(drawableList: ArrayList<Drawable>)

}