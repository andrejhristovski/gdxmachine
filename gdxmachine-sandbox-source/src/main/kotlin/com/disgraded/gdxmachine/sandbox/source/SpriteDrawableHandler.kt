package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.graphics.Drawable
import com.disgraded.gdxmachine.framework.graphics.DrawableHandler

class SpriteDrawableHandler : DrawableHandler {

    override fun begin() {

    }

    override fun draw(drawable: Drawable) {
        val sprite = drawable as Sprite
    }

    override fun end(): Int {
        return 1
    }

    override fun dispose() {

    }
}