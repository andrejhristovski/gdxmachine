package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.graphics.Color as GdxColor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color

class Text(private val bitmapFont: BitmapFont, displayText: String = "[YOUR TEXT HERE]") : Drawable2D() {

    private val glyphLayout = GlyphLayout(bitmapFont, displayText)

    var color = Color.WHITE
        set(value) {
            field = value
            bitmapFont.color = GdxColor(value.r, value.g, value.b, value.a)
        }

    var displayText = displayText
        set(value) {
            field = value
            glyphLayout.setText(bitmapFont, value)
        }

    fun getGlyph(): GlyphLayout = glyphLayout

    fun getBitmapFont(): BitmapFont = bitmapFont
}