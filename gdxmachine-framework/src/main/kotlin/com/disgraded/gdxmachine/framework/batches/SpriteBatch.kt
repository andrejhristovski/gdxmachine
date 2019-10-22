package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.graphics.VertexAttribute as Attribute
import com.badlogic.gdx.graphics.VertexAttributes as Attributes
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.utils.Corner

class SpriteBatch : Batch {

    private val core = Core

    private val bufferSize = 20
    private val verticesPerBuffer = 4
    private val indicesPerBuffer = 6
    private val maxCalls = Short.MAX_VALUE / verticesPerBuffer
    private var bufferedCalls = 0
    private var gpuCalls = 0

    private val mesh: Mesh
    private val vertices: FloatArray
    private val indices: ShortArray

    private lateinit var projectionMatrix: Matrix4
    private val defaultShader: Shader
    private var shader: Shader
    private var cachedTexture: Texture? = null
    init {
        val maxVertices = verticesPerBuffer * maxCalls
        val maxIndices = indicesPerBuffer * maxCalls
        val vertexAttributes = Attributes(
                Attribute(Attributes.Usage.Position, 2, Shader.POSITION()),
                Attribute(Attributes.Usage.ColorPacked, 4, Shader.COLOR()),
                Attribute(Attributes.Usage.TextureCoordinates, 2, Shader.TEXCOORD())
        )
        mesh = Mesh(false, maxVertices, maxIndices, vertexAttributes)
        vertices = FloatArray(maxCalls * bufferSize)
        indices = ShortArray(maxCalls * indicesPerBuffer)
        for (i in indices.indices) {
            val module = i % 6
            val idx = (i / 6) * 4
            if (module == 0 || module == 5) indices[i] = idx.toShort()
            if (module == 1) indices[i] = (idx + 1).toShort()
            if (module == 2 || module == 3) indices[i] = (idx + 2).toShort()
            if (module == 4) indices[i] = (idx + 3).toShort()
        }
        defaultShader = getDefaultShader()
        shader = defaultShader
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun draw(drawable: Drawable) {
        val sprite = drawable as Sprite
        checkShader(sprite.shader)
        checkTexture(sprite.getTexture().texture)
        if (bufferedCalls >= maxCalls) flush()
        append(sprite)
    }

    override fun end(): Int {
        if (bufferedCalls > 0) flush()
        val totalGpuCalls = gpuCalls
        gpuCalls = 0
        return  totalGpuCalls
    }

    override fun dispose() = mesh.dispose()

    private fun getDefaultShader(): Shader {
        return core.graphics.getShader("sprite")
    }

    private fun checkShader(shader: Shader?) {
        if (shader == null) {
            if (this.shader !== defaultShader) {
                flush()
                this.shader = defaultShader
            }
            return
        }
        if (shader !== this.shader) {
            flush()
            this.shader = shader
        }
    }

    private fun checkTexture(texture: Texture) {
        if (this.cachedTexture !== texture) {
            flush()
            cachedTexture = texture
        }
    }

    private fun append(sprite: Sprite) {
        val idx = bufferedCalls * bufferSize

        val sizeX = sprite.getTexture().regionWidth * sprite.scaleX
        val sizeY = sprite.getTexture().regionHeight * sprite.scaleY

        var x1 = 0 - (sizeX * sprite.anchorX)
        var y1 = 0 - (sizeY * sprite.anchorY)
        var x2 = x1
        var y2 = y1 + sizeY
        var x3 = x1 + sizeX
        var y3 = y1 + sizeY
        var x4 = x1 + sizeX
        var y4 = y1

        if (sprite.angle != 0f) {
            val cos = MathUtils.cosDeg(sprite.angle)
            val sin = MathUtils.sinDeg(sprite.angle)
            val rx1 = cos * x1 - sin * y1
            val ry1 = sin * x1 + cos * y1
            val rx2 = cos * x2 - sin * y2
            val ry2 = sin * x2 + cos * y2
            val rx3 = cos * x3 - sin * y3
            val ry3 = sin * x3 + cos * y3
            x1 = rx1
            y1 = ry1
            x2 = rx2
            y2 = ry2
            x3 = rx3
            y3 = ry3
            x4 = x1 + (x3 - x2)
            y4 = y3 - (y2 - y1)
        }

        x1 += sprite.x
        x2 += sprite.x
        x3 += sprite.x
        x4 += sprite.x
        y1 += sprite.y
        y2 += sprite.y
        y3 += sprite.y
        y4 += sprite.y

        vertices[idx] = x1
        vertices[idx + 1] = y1
        vertices[idx + 2] = sprite.getColor(Corner.BOTTOM_LEFT).toFloatBits()
        vertices[idx + 3] = sprite.getTexture().u
        vertices[idx + 4] = sprite.getTexture().v2

        vertices[idx + 5] = x2
        vertices[idx + 6] = y2
        vertices[idx + 7] = sprite.getColor(Corner.TOP_LEFT).toFloatBits()
        vertices[idx + 8] = sprite.getTexture().u
        vertices[idx + 9] = sprite.getTexture().v

        vertices[idx + 10] = x3
        vertices[idx + 11] = y3
        vertices[idx + 12] = sprite.getColor(Corner.TOP_RIGHT).toFloatBits()
        vertices[idx + 13] = sprite.getTexture().u2
        vertices[idx + 14] = sprite.getTexture().v

        vertices[idx + 15] = x4
        vertices[idx + 16] = y4
        vertices[idx + 17] = sprite.getColor(Corner.BOTTOM_RIGHT).toFloatBits()
        vertices[idx + 18] = sprite.getTexture().u2
        vertices[idx + 19] = sprite.getTexture().v2
        bufferedCalls++
    }

    private fun flush() {
        if (bufferedCalls == 0) return
        gpuCalls++
        val verticesCount = bufferedCalls * bufferSize
        val indicesCount = bufferedCalls * indicesPerBuffer

        shader.begin()
        cachedTexture!!.bind(0)
        shader.setUniformMatrix("u_projectionTrans", projectionMatrix)
        shader.setUniformi("u_texture", 0)

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        mesh.render(shader, GL20.GL_TRIANGLES, 0, indicesCount)
        shader.end()
        bufferedCalls = 0
    }
}