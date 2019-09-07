package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.disgraded.gdxmachine.core.Context
import com.disgraded.gdxmachine.core.api.graphics.utils.Color
import com.disgraded.gdxmachine.core.api.graphics.Viewport
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable2D
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.scene.Scene

class TestScene : Scene {

    private lateinit var background: Sprite
    private var spriteList = arrayListOf<Sprite>()
    private lateinit var render: Viewport.Api
    private lateinit var render2: Viewport.Api
    private lateinit var context: Context

    override fun initialize(context: Context) {
        this.context = context
        render = context.graphics.getContext()
        render2 =context.graphics.getContext("second")
//        render.project(0f, 0f, .5f, 1f, 2f, 2f)
//        render2.project(.5f, 0f, .5f, 1f)

        render2.visible = false

        val texture = context.resources.get<Texture>("initial", "rock")

        background = Sprite(context.resources.get<Texture>("initial", "background"))
        background.setColor(Color.YELLOW)
        background.getColor().alpha = .5f


        var startX = -1280 / 2f
//        for (i in 0..100) {
//            val sprite = Sprite(TextureRegion(texture, 100, 200))
//            sprite.setColor(Drawable2D.Corner.TOP_LEFT, Color.random())
//            sprite.setColor(Drawable2D.Corner.BOTTOM_RIGHT, Color.random())
//            sprite.setColor(Drawable2D.Corner.TOP_RIGHT, Color.random())
//            sprite.x = startX
//            sprite.y = -200f
//            spriteList.add(sprite)
//            startX += 100
//        }
//
//        startX = -1280 / 2f
//        for (i in 0..50) {
//            val sprite = Sprite(TextureRegion(texture, 50, 200))
//            sprite.setColor(Drawable2D.Corner.TOP_LEFT, Color.random())
//            sprite.setColor(Drawable2D.Corner.BOTTOM_RIGHT, Color.random())
//            sprite.setColor(Drawable2D.Corner.TOP_RIGHT, Color.random())
//            sprite.x = startX
//            spriteList.add(sprite)
//            startX += 50
//        }
//
        startX = -1280 / 2f
        for (i in 0..500) {
            val sprite = Sprite(TextureRegion(texture, 60, 200))
            sprite.setColor(Drawable2D.Corner.TOP_LEFT, Color.random())
            sprite.setColor(Drawable2D.Corner.BOTTOM_RIGHT, Color.random())
            sprite.setColor(Drawable2D.Corner.TOP_RIGHT, Color.random())
            sprite.x = startX
            sprite.y = 200f
            spriteList.add(sprite)
            startX += 60
        }
    }

    override fun update(deltaTime: Float) {
        render.getCamera().position.x += 50 * deltaTime
//        background.x = render.getCamera().position.x
        render.draw(background)
        render2.draw(background)
        for (sprite in spriteList) {
            render.draw(sprite)
        }

    }

    override fun destroy() {

    }
}