package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class Text(val bitmapFont: BitmapFont) : Drawable(Type.TEXT) {
    var content = ""
    var color = Color.WHITE
}