package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light

class DefferedLightBatch : DrawableBatch {

    fun applyLights(lightList: ArrayList<Light>) {

    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun flush() {

    }

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}