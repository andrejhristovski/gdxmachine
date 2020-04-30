package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.core.graphics.utils.Transform2D
import com.disgraded.gdxmachine.framework.drawables.*
import com.badlogic.gdx.math.Rectangle as Rectangle2D
import com.badlogic.gdx.math.Circle as Circle2D
import com.badlogic.gdx.math.Ellipse as Ellipse2D
import com.badlogic.gdx.math.Polygon as Polygon2D

class ShapeBatch(private val mode: Mode = Mode.DIFFUSE) : Batch {

    enum class Mode {
        DIFFUSE, BUMP
    }

    private val forcedColor: Color

    private val shapeRenderer = ShapeRenderer()
    private val blendEquation: Int
    private val blendSrcRGB: Int
    private val blendDstRGB: Int
    private val blendSrcAlpha: Int
    private val blendDstAlpha: Int

    private var calls = 0
    private val transformMatrix = Matrix4()
    private var projectionMatrix = Matrix4()

    init {
        when(mode) {
            Mode.DIFFUSE -> {
                forcedColor = Color.TRANSPARENT
                blendEquation = GL20.GL_FUNC_ADD
                blendSrcRGB = GL20.GL_SRC_ALPHA
                blendDstRGB = GL20.GL_ONE_MINUS_SRC_ALPHA
                blendSrcAlpha = GL20.GL_SRC_ALPHA
                blendDstAlpha = GL20.GL_ONE_MINUS_SRC_ALPHA
            }
            Mode.BUMP -> {
                forcedColor = Color.NORMAL
                blendEquation = GL20.GL_FUNC_ADD
                blendSrcRGB = GL20.GL_SRC_ALPHA
                blendDstRGB = GL20.GL_ONE_MINUS_SRC_ALPHA
                blendSrcAlpha = GL20.GL_ONE
                blendDstAlpha = GL20.GL_ONE_MINUS_SRC_ALPHA
            }
        }

    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun draw(drawable: Drawable) {
        val shape = drawable as Shape
        if (!shape.isUpdated()) shape.update()
        val shapeType = when(shape.style) {
            Shape.Style.FILLED -> ShapeRenderer.ShapeType.Filled
            Shape.Style.LINE -> ShapeRenderer.ShapeType.Line
            Shape.Style.POINT -> ShapeRenderer.ShapeType.Point
        }

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendEquation(blendEquation)
        Gdx.gl.glBlendFuncSeparate(blendSrcRGB, blendDstRGB, blendSrcAlpha, blendDstAlpha)
        shapeRenderer.begin(shapeType)
        if (forcedColor != Color.TRANSPARENT) {
            shapeRenderer.color.set(forcedColor.r, forcedColor.g, forcedColor.b, forcedColor.a * shape.opacity)
        } else {
            shapeRenderer.color.set(shape.color.r, shape.color.g, shape.color.b, shape.color.a * shape.opacity)
        }
        when(shape.getShapeType()) {
            Circle::class -> drawCircle(shape.absolute, shape.getShape2D() as Circle2D)
            Ellipse::class -> drawEllipse(shape.absolute, shape.getShape2D() as Ellipse2D)
            Polygon::class -> drawPolygon(shape.absolute, shape.getShape2D() as Polygon2D)
            Rectangle::class -> drawRectangle(shape.absolute, shape.getShape2D() as Rectangle2D)
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

    private fun drawRectangle(transform2D: Transform2D, rectangle: Rectangle2D) {
        applyTranslation(transform2D)
        shapeRenderer.rect(-rectangle.width * transform2D.anchorX, -rectangle.height * transform2D.anchorY,
                rectangle.width, rectangle.height)

    }

    private fun drawCircle(transform2D: Transform2D, circle: Circle2D) {
        applyTranslation(transform2D)
        shapeRenderer.circle(circle.radius - (circle.radius * 2 * transform2D.anchorX),
                circle.radius - (circle.radius * 2 * transform2D.anchorY), circle.radius)
    }

    private fun drawEllipse(transform2D: Transform2D, ellipse: Ellipse2D) {
        applyTranslation(transform2D)
        shapeRenderer.ellipse(-ellipse.width * transform2D.anchorX, -ellipse.height * transform2D.anchorY,
                ellipse.width, ellipse.height, transform2D.angle)
    }

    private fun drawPolygon(transform2D: Transform2D, polygon: Polygon2D) {
        applyTranslation(transform2D)
        shapeRenderer.polygon(polygon.vertices)
    }

    private fun applyTranslation(transform2D: Transform2D) {
        transformMatrix.idt()
        transformMatrix.setToTranslation(transform2D.x, transform2D.y, 0f)
        transformMatrix.scale(transform2D.scaleX, transform2D.scaleY, 1f)
        transformMatrix.rotate(0f, 0f, 1f, transform2D.angle)
        shapeRenderer.projectionMatrix = transformMatrix.mulLeft(projectionMatrix)
    }
}