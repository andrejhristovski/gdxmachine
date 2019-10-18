package com.disgraded.gdxmachine.framework.graphics

import com.badlogic.gdx.utils.Disposable

interface Renderer: Disposable {

    fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int

}