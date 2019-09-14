package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout

class Text(private val bitmapFont: BitmapFont, displayText: String = "[YOUR TEXT HERE]") : Drawable(Type.TEXT) {

    private val glyphLayout = GlyphLayout(bitmapFont, displayText)

    var displayText = displayText
        set(value) {
            field = value
            glyphLayout.setText(bitmapFont, value)
        }

    fun getGlyph(): GlyphLayout = glyphLayout

    fun getBitmapFont(): BitmapFont = bitmapFont
}