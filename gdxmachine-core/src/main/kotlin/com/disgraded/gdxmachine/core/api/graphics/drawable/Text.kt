package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout

class Text(val bitmapFont: BitmapFont) : Drawable(Type.TEXT) {

    val glyphLayout = GlyphLayout(bitmapFont, "")

    var content = ""
        set(value) {
            field = value
            glyphLayout.setText(bitmapFont, value)
        }
}