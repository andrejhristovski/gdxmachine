package com.disgraded.gdxmachine.core.api.graphics.drawable

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class Sprite : Drawable {

    enum class Filter(val type: String) { TINT("tint"), FILL("fill"), GREYSCALE("greyscale"),
        SEPIA("sepia"), INVERT("invert") }

    private var texture: TextureRegion
    private var mask: TextureRegion? = null
    private var normalMap: TextureRegion? = null
    private val colorMap = hashMapOf<Corner, Color>()
    var filter = Filter.TINT

    init {
        colorMap[Corner.TOP_LEFT] = Color.WHITE
        colorMap[Corner.TOP_RIGHT] = Color.WHITE
        colorMap[Corner.BOTTOM_LEFT] = Color.WHITE
        colorMap[Corner.BOTTOM_RIGHT] = Color.WHITE
    }

    constructor(texture: Texture): super(Type.SPRITE) {
        this.texture = TextureRegion(texture)
    }

    constructor(textureRegion: TextureRegion): super(Type.SPRITE) {
        this.texture = textureRegion
    }

    fun setTexture(texture: Texture) {
        this.texture = TextureRegion(texture)
    }

    fun setTexture(textureRegion: TextureRegion) {
        this.texture = textureRegion
    }

    fun getTexture(): TextureRegion {
        return texture
    }

    fun setMask(mask: Texture) {
        this.mask = TextureRegion(mask)
    }

    fun setMask(mask: TextureRegion) {
        this.mask = mask
    }

    fun getMask() : TextureRegion? {
        return mask
    }

    fun setNormalMap(normalMap: TextureRegion) {
        this.normalMap = normalMap
    }

    fun setNormalMap(normalMap: Texture) {
        this.normalMap = TextureRegion(normalMap)
    }

    fun getNormalMap(): TextureRegion? {
        return normalMap
    }

    fun setColor(color: Color) {
        colorMap[Corner.TOP_LEFT] = color
        colorMap[Corner.TOP_RIGHT] = color
        colorMap[Corner.BOTTOM_LEFT] = color
        colorMap[Corner.BOTTOM_RIGHT] = color
    }

    fun setColor(corner: Corner, color: Color) {
        when(corner) {
            Corner.TOP_LEFT -> colorMap[Corner.TOP_LEFT] = color
            Corner.TOP_RIGHT -> colorMap[Corner.TOP_RIGHT] = color
            Corner.BOTTOM_LEFT -> colorMap[Corner.BOTTOM_LEFT] = color
            Corner.BOTTOM_RIGHT -> colorMap[Corner.BOTTOM_RIGHT] = color
        }
    }

    fun getColor(): Color {
        return colorMap[Corner.TOP_LEFT]!!
    }

    fun getColor(corner: Corner): Color {
        return when(corner) {
            Corner.TOP_LEFT -> colorMap[Corner.TOP_LEFT]!!
            Corner.TOP_RIGHT -> colorMap[Corner.TOP_RIGHT]!!
            Corner.BOTTOM_LEFT -> colorMap[Corner.BOTTOM_LEFT]!!
            Corner.BOTTOM_RIGHT -> colorMap[Corner.BOTTOM_RIGHT]!!
        }
    }
}