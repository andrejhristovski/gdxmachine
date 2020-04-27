package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.utils.Corner
import com.disgraded.gdxmachine.framework.core.Prototype
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Drawable2D
import kotlin.reflect.KClass

open class Sprite : Drawable2D(), Prototype<Sprite> {

    companion object {
        const val DIFFUSE_TEXTURE = "_diffuse"
        const val NORMAL_TEXTURE = "_normal"
        const val MASK_TEXTURE = "_mask"
    }

    private val colorMap = hashMapOf<Corner, Color>()

    private val textureMap = hashMapOf<String, TextureRegion>()


    var flipX = false
    var flipY = false

    init {
        colorMap[Corner.TOP_LEFT] = Color.WHITE
        colorMap[Corner.TOP_RIGHT] = Color.WHITE
        colorMap[Corner.BOTTOM_LEFT] = Color.WHITE
        colorMap[Corner.BOTTOM_RIGHT] = Color.WHITE
    }

    fun setTexture(texture: Texture, key: String = DIFFUSE_TEXTURE) {
        setTexture(TextureRegion(texture), key)
    }

    fun setTexture(textureRegion: TextureRegion, key: String = DIFFUSE_TEXTURE) {
        textureMap[key] = textureRegion
    }

    fun getTexture(key: String = DIFFUSE_TEXTURE): TextureRegion? {
        val texture = textureMap[key]
        if (texture != null) {
            val flipX = this.flipX != texture.isFlipX
            val flipY = this.flipY != texture.isFlipY
            texture.flip(flipX, flipY)
        }
        return texture
    }

    fun removeTexture(key: String = DIFFUSE_TEXTURE) = textureMap.remove(key)

    fun clearAllTextures() = textureMap.clear()

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

    override fun getType(): KClass<out Drawable> = Sprite::class

    override fun copy(): Sprite {
        val sprite = Sprite()
        sprite.inherit(this)
        return sprite
    }

    override fun inherit(obj: Sprite) {
        setColor(Corner.TOP_LEFT, obj.getColor(Corner.TOP_LEFT))
        setColor(Corner.TOP_RIGHT, obj.getColor(Corner.TOP_RIGHT))
        setColor(Corner.BOTTOM_LEFT, obj.getColor(Corner.BOTTOM_LEFT))
        setColor(Corner.BOTTOM_RIGHT, obj.getColor(Corner.BOTTOM_RIGHT))

        for (key in obj.textureMap.keys) {
            textureMap[key] = obj.textureMap[key]!!
        }

        super.inherit(obj)
    }
}