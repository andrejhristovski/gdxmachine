package com.disgraded.gdxmachine.framework.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.utils.Corner
import com.disgraded.gdxmachine.framework.core.Prototype
import com.disgraded.gdxmachine.framework.utils.Animation2D

open class Sprite : Drawable2D(), Prototype<Sprite> {

    private var textureRegion: TextureRegion? = null
    private val colorMap = hashMapOf<Corner, Color>()

    private val animationMap = HashMap<String, Animation2D>()
    private var activeAnim: Animation2D? = null

    // TODO: implement flip on texture & anim

    init {
        colorMap[Corner.TOP_LEFT] = Color.WHITE
        colorMap[Corner.TOP_RIGHT] = Color.WHITE
        colorMap[Corner.BOTTOM_LEFT] = Color.WHITE
        colorMap[Corner.BOTTOM_RIGHT] = Color.WHITE
    }

    fun addAnim(animation: Animation2D) {
        if (animationMap.containsKey(animation.key)) {
            throw RuntimeException("Animation ${animation.key} already exist")
        }
        animationMap[animation.key] = animation
    }

    fun removeAnim(key: String) {
        if (!animationMap.containsKey(key)) {
            throw RuntimeException("Animation $key doesn't exist")
        }
        animationMap.remove(key)
    }

    fun getAnim(key: String): Animation2D {
        if (!animationMap.containsKey(key)) {
            throw RuntimeException("Animation $key doesn't exist")
        }
        return animationMap[key]!!
    }

    fun getAnimList(): MutableSet<String> {
        return animationMap.keys
    }

    fun getAnim(): Animation2D? = activeAnim

    fun play(key: String) {
        if (!animationMap.containsKey(key)) {
            throw RuntimeException("Animation $key doesn't exist")
        }
        activeAnim = animationMap[key]
    }

    fun stop() {
        activeAnim = null
    }

    fun setTexture(texture: Texture) {
        setTexture(TextureRegion(texture))
    }

    fun setTexture(textureRegion: TextureRegion) {
        this.textureRegion = textureRegion
    }

    fun getTexture(): TextureRegion {
        if (activeAnim != null) {
            return activeAnim!!.getTexture()
        }
        if (textureRegion == null) throw RuntimeException("Texture doesn't exist!")
        return textureRegion!!
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

    override fun copy(): Sprite {
        val sprite = Sprite()
        sprite.inherit(this)
        return sprite
    }

    override fun inherit(obj: Sprite) {
        setTexture(obj.getTexture())
        setColor(obj.getColor(Corner.TOP_LEFT))
        setColor(obj.getColor(Corner.TOP_RIGHT))
        setColor(obj.getColor(Corner.BOTTOM_LEFT))
        setColor(obj.getColor(Corner.BOTTOM_RIGHT))
        super.inherit(obj)
    }
}