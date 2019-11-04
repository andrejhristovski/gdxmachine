package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.utils.Disposable

interface Renderer: Disposable {

    fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int

}