package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.graphics.Color as GdxColor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.Align
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.core.Prototype
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Drawable2D
import java.lang.Exception
import kotlin.reflect.KClass

open class Text: Drawable2D(), Prototype<Text> {

    var font: BitmapFont? = null
    val glyph = GlyphLayout()

    var color: Color = Color.WHITE
        set(value) {
            field = value
            updateGlyph()
        }

    override var opacity: Float = 1f
        set(value) {
            super.opacity = value
            field = super.opacity
            updateGlyph()
        }

    var content: String = "[YOUR TEXT HERE]"
        set(value) {
            field = value
            updateGlyph()
        }

    private val gdxColor: GdxColor = GdxColor()

    var width: Float = 0f
        set(value) {
            field = value
            updateGlyph()
        }

    var limit = 0
        set(value) {
            field = value
            updateGlyph()
        }

    var offset = 0
        set(value) {
            field = value
            updateGlyph()
        }

    var align = Align.left
        set(value) {
            field = value
            updateGlyph()
        }

    var wrap = false
        set(value) {
            field = value
            updateGlyph()
        }

    var truncate: String? = null
        set(value) {
            field = value
            updateGlyph()
        }

    init {
        updateGlyph()
    }

    private fun updateGlyph() {
        gdxColor.set(color.r, color.g, color.b, color.a * opacity)
        var limitLength = content.length
        if (limit != 0) {
            limitLength = limit
        }
        var start = 0
        if (offset != 0) {
            start = offset
        }
        try {
            font!!.color = gdxColor
            glyph.setText(font, content, start, limitLength, font!!.color, width, align, wrap, truncate)
        } catch (e: Exception) {}
    }

    override fun getType(): KClass<out Drawable> = Text::class

    override fun copy(): Text {
        val text = Text()
        text.inherit(this)
        return text
    }

    override fun inherit(obj: Text) {
        font = obj.font
        color = obj.color
        content = obj.content
        width = obj.width
        limit = obj.limit
        offset = obj.offset
        align = obj.align
        wrap = obj.wrap
        truncate = obj.truncate
        super.inherit(obj)
    }
}