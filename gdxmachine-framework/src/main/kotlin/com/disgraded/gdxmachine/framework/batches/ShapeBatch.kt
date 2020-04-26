package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.drawables.*
import com.badlogic.gdx.math.Rectangle as Rectangle2D
import com.badlogic.gdx.math.Circle as Circle2D
import com.badlogic.gdx.math.Ellipse as Ellipse2D
import com.badlogic.gdx.math.Polygon as Polygon2D

class ShapeBatch(private val forcedColor: Color = Color.TRANSPARENT) : Batch {

    private val shapeRenderer = ShapeRenderer()
    private var calls = 0
    private val transformMatrix = Matrix4()
    private var projectionMatrix = Matrix4()

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun draw(drawable: Drawable) {
        val shape = drawable as Shape
        val shapeType = when(shape.style) {
            Shape.Style.FILLED -> ShapeRenderer.ShapeType.Filled
            Shape.Style.LINE -> ShapeRenderer.ShapeType.Line
            Shape.Style.POINT -> ShapeRenderer.ShapeType.Point
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(shapeType)
        if (forcedColor != Color.TRANSPARENT) {
            shapeRenderer.color.set(forcedColor.r, forcedColor.g, forcedColor.b, forcedColor.a * shape.opacity)
        } else {
            shapeRenderer.color.set(shape.color.r, shape.color.g, shape.color.b, shape.color.a * shape.opacity)
        }
        when(shape.getShapeType()) {
            Circle::class -> drawCircle(shape, shape.getShape2D() as Circle2D)
            Ellipse::class -> drawEllipse(shape, shape.getShape2D() as Ellipse2D)
            Polygon::class -> drawPolygon(shape, shape.getShape2D() as Polygon2D)
            Rectangle::class -> drawRectangle(shape, shape.getShape2D() as Rectangle2D)
            else -> throw RuntimeException("Unsupported shape type ${shape.getType()}")
        }
        calls++
        shapeRenderer.end()
    }

    override fun end(): Int {
        val totalCalls = calls
        calls = 0
        return totalCalls
    }

    override fun dispose() = shapeRenderer.dispose()

    private fun drawRectangle(shape: Shape, rectangle: Rectangle2D) {
        applyTranslation(shape)
        shapeRenderer.rect(-rectangle.width * shape.anchorX, -rectangle.height * shape.anchorY,
                rectangle.width, rectangle.height)

    }

    private fun drawCircle(shape: Shape, circle: Circle2D) {
        applyTranslation(shape)
        shapeRenderer.circle(circle.radius - (circle.radius * 2 * shape.anchorX),
                circle.radius - (circle.radius * 2 * shape.anchorY), circle.radius)
    }

    private fun drawEllipse(shape: Shape, ellipse: Ellipse2D) {
        applyTranslation(shape)
        shapeRenderer.ellipse(-ellipse.width * shape.anchorX, -ellipse.height * shape.anchorY,
                ellipse.width, ellipse.height, shape.angle)
    }

    private fun drawPolygon(shape: Shape, polygon: Polygon2D) {
        applyTranslation(shape)
        shapeRenderer.polygon(polygon.vertices)
    }

    private fun applyTranslation(shape: Shape) {
        transformMatrix.idt()
        transformMatrix.setToTranslation(shape.x, shape.y, 0f)
        transformMatrix.scale(shape.scaleX, shape.scaleY, 1f)
        transformMatrix.rotate(0f, 0f, 1f, shape.angle)
        shapeRenderer.projectionMatrix = transformMatrix.mulLeft(projectionMatrix)
    }
}