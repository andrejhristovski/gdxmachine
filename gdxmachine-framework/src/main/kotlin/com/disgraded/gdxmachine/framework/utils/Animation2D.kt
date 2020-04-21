package com.disgraded.gdxmachine.framework.utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.framework.core.Core
import com.badlogic.gdx.utils.Array as GdxArray

class Animation2D(val key: String, frames: GdxArray<TextureRegion>) : Animation<TextureRegion>(0.025f, frames) {

    companion object {
        fun split(texture: Texture, width: Int, height: Int, frameCount: Int): GdxArray<TextureRegion> {
            val frameMatrix = TextureRegion.split(texture, width, height)
            return createFrameArray(frameMatrix, frameCount)
        }

        fun split(texture: TextureRegion, width: Int, height: Int, frameCount: Int): GdxArray<TextureRegion> {
            val frameMatrix = texture.split(width, height)
            return createFrameArray(frameMatrix, frameCount)
        }

        private fun createFrameArray(frameMatrix: Array<Array<TextureRegion>>, frameCount: Int): GdxArray<TextureRegion> {
            val frames = GdxArray<TextureRegion>()
            for (textureArray in frameMatrix) {
                for (texture in textureArray) {
                    if (frames.size < frameCount) {
                        frames.add(texture)
                    } else {
                        break
                    }
                }
            }
            return frames
        }
    }

    private var accumulator: Float = 0f
    var speed = 1f

    init {
        playMode = PlayMode.LOOP
    }

    fun getTexture(): TextureRegion {
        return getKeyFrame(accumulator) ?: throw RuntimeException("There is no available animation texture at the moment")
    }

    fun update(deltaTime: Float) {
        accumulator += deltaTime * speed
        if (accumulator > animationDuration * 100) {
            accumulator -= animationDuration * 100
        }
    }
}