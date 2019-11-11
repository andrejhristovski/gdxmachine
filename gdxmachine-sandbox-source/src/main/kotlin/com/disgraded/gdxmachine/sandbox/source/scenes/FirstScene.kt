package com.disgraded.gdxmachine.sandbox.source.scenes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Circle
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.Mask
import com.disgraded.gdxmachine.framework.drawables.Shape
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.drawables.Text
import com.disgraded.gdxmachine.framework.scenes.Scene

class FirstScene : Scene() {

    private lateinit var sprite: Sprite
    private lateinit var text: Text
    private lateinit var shape: Shape<Circle>

    override fun initialize() {
        val assets = core.resources.get("core")
        sprite = Sprite()
        sprite.setTexture(assets.get<Texture>("no_image"))

        text = Text(assets.get("sans"))

        shape = Shape(Circle(0f, 0f, 200f))
        shape.style = Shape.Style.LINE
        shape.color = Color.RED
    }

    override fun update(deltaTime: Float) {
        text.displayText = "FPS: ${core.graphics.getFPS()}"
        mainLayer.draw(sprite)
        mainLayer.draw(text)
        mainLayer.draw(shape)
    }

    override fun destroy() {

    }
}