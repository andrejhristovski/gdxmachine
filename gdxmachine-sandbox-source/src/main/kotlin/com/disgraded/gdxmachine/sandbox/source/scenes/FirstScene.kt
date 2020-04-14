package com.disgraded.gdxmachine.sandbox.source.scenes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
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
    private lateinit var shape2: Shape<Rectangle>
    private lateinit var polygon: Shape<Polygon>
    private lateinit var text2: Text

    override fun initialize() {
        val assets = core.resources.get("core")
        sprite = Sprite()
        sprite.setTexture(assets.get<Texture>("no_image"))
        sprite.opacity = .5f

        text = Text(assets.get("sans"))
        text.color = Color.LIME
        text.setPosition(500f, 300f)

        shape = Shape(Circle(0f, 0f, 200f))
        shape.style = Shape.Style.LINE
        shape.color = Color.CYAN
        shape.opacity = .5f

        shape2 = Shape(Rectangle(0f, 0f, 100f, 100f))
        shape2.x = -100f
        shape2.color = Color.DEEP_PURPLE
        shape2.opacity = .2f

        text2 = text.copy()
        text2.setPosition(300f, 300f)
        text2.opacity = .5f

        val polygonVertices = floatArrayOf(-10f, -10f, 10f, -10f, 5f, 5f, 10f, 10f, -10f, 10f)
        polygon = Shape(Polygon())
        polygon.shape.vertices = polygonVertices
        polygon.style = Shape.Style.LINE
        polygon.setScale(25f)
        polygon.angle = 44f
        polygon.x = 10f
    }

    override fun update(deltaTime: Float) {
        polygon.angle += 10f * deltaTime

        text.content = "FPS: ${core.graphics.getFPS()} :: GPU: ${core.graphics.getGPUCalls()}"
        mainLayer.draw(sprite)
        mainLayer.draw(text)
        mainLayer.draw(shape)
        mainLayer.draw(shape2)
        mainLayer.draw(text2)
        mainLayer.draw(polygon)
    }

    override fun destroy() {

    }
}