package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.*
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.drawables.Shape

class ShapeBatch : Batch {

    private val shapeRenderer = ShapeRenderer()
    private var calls = 0

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        shapeRenderer.projectionMatrix = projectionMatrix
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
        shapeRenderer.color.set(shape.color.r, shape.color.g, shape.color.b, shape.color.a * shape.opacity)
        when(shape.getType()) {
            Circle::class -> drawCircle(shape, shape.getShape2D() as Circle)
            Ellipse::class -> drawEllipse(shape, shape.getShape2D() as Ellipse)
            Polygon::class -> drawPolygon(shape, shape.getShape2D() as Polygon)
            Rectangle::class -> drawRectangle(shape, shape.getShape2D() as Rectangle)
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

    private fun drawRectangle(shape: Shape, rectangle: Rectangle) {
        shapeRenderer.rect(shape.x, shape.y, shape.anchorX, shape.anchorY, rectangle.width,
                rectangle.height, shape.scaleX, shape.scaleY, shape.angle)
    }

    private fun drawCircle(shape: Shape, circle: Circle) {
        shapeRenderer.circle(shape.x, shape.y, circle.radius)
    }

    private fun drawEllipse(shape: Shape, ellipse: Ellipse) {
        shapeRenderer.ellipse(shape.x, shape.y, ellipse.width, ellipse.height, shape.angle)
    }

    private fun drawPolygon(shape: Shape, polygon: Polygon) {
        shapeRenderer.polygon(polygon.transformedVertices)
    }
}