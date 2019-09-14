package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Corner
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite

class SpriteStandardRenderer : Renderer {

    companion object {
        private const val BUFFER_SIZE = 20
        private const val VERTICES_PER_BUFFER = 4
        private const val INDICES_PER_BUFFER = 6
        private const val MAX_BUFFERED_CALLS = Short.MAX_VALUE / VERTICES_PER_BUFFER
    }

    private val shaderFactory = ShaderFactory.getInstance()

    override var active: Boolean = false
    private var gpuCalls = 0
    private var bufferedCalls = 0

    private val mesh: Mesh
    private val vertices: FloatArray
    private val indices: ShortArray
    private var shaderProgram: ShaderProgram
    private val shaderVertexPrefix = "sprite_standard"
    private var shaderFragmentPrefix = "sprite_standard.tint"
    private lateinit var projectionMatrix: Matrix4

    private var cachedTexture: Texture? = null

    init {
        val maxVertices = VERTICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val maxIndices = INDICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val vertexAttributes = VertexAttributes(
                VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE)
        )

        mesh = Mesh(false, maxVertices, maxIndices, vertexAttributes)

        vertices = FloatArray(MAX_BUFFERED_CALLS * BUFFER_SIZE)
        indices = ShortArray(MAX_BUFFERED_CALLS * INDICES_PER_BUFFER)
        for (i in indices.indices) {
            val module = i % 6
            val idx = (i / 6) * 4
            if (module == 0 || module == 5) indices[i] = idx.toShort()
            if (module == 1) indices[i] = (idx + 1).toShort()
            if (module == 2 || module == 3) indices[i] = (idx + 2).toShort()
            if (module == 4) indices[i] = (idx + 3).toShort()
        }

        shaderProgram = shaderFactory.get(shaderVertexPrefix, shaderFragmentPrefix)
    }

    override fun begin() {
        if(active) throw RuntimeException("The renderer is already active")
        gpuCalls = 0
        shaderProgram.begin()
        active = true
    }

    override fun draw(drawable: Drawable) {
        val sprite = drawable as Sprite
        validateShaderProgram(sprite)
        validateTexture(sprite.getTexture())
        if (bufferedCalls == MAX_BUFFERED_CALLS) flush()
        appendVertices(sprite)
    }

    override fun end(): Int {
        if(!active) throw RuntimeException("The renderer isn't active")
        if (bufferedCalls > 0) flush()
        cachedTexture = null
        active = false
        shaderProgram.end()
        val calculatedGpuCalls = gpuCalls
        gpuCalls = 0
        return calculatedGpuCalls
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        this.projectionMatrix = projectionMatrix
    }

    override fun dispose() = mesh.dispose()

    private fun validateShaderProgram(sprite: Sprite) {
        val fragmentPrefix = "sprite_standard.${sprite.filter.type}"
        if (fragmentPrefix != shaderFragmentPrefix) {
            flush()
            shaderFragmentPrefix = fragmentPrefix
            shaderProgram.end()
            shaderProgram = shaderFactory.get(shaderVertexPrefix, shaderFragmentPrefix)
            shaderProgram.begin()
        }
    }

    private fun validateTexture(textureRegion: TextureRegion) {
        if (cachedTexture !== textureRegion.texture) {
            flush()
            cachedTexture = textureRegion.texture
        }
    }

    private fun appendVertices(sprite: Sprite) {
        val idx = bufferedCalls * BUFFER_SIZE
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

        if (sprite.rotation != 0f) {
            val cos = MathUtils.cosDeg(sprite.rotation)
            val sin = MathUtils.sinDeg(sprite.rotation)
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
        if(bufferedCalls == 0) return
        gpuCalls++

        val indicesCount = bufferedCalls * INDICES_PER_BUFFER
        val verticesCount = bufferedCalls * BUFFER_SIZE

        cachedTexture!!.bind(0)

        shaderProgram.setUniformMatrix("u_projectionTrans", projectionMatrix);
        shaderProgram.setUniformi("u_texture", 0)

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, indicesCount)
        bufferedCalls = 0
    }
}