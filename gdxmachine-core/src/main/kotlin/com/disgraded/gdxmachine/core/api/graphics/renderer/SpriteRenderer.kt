package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.NumberUtils
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite


class SpriteRenderer : Renderer<Sprite> {

    companion object {
        private const val MAX_BUFFERED_CALLS = 50
        private const val BUFFER_SIZE = 20
        private const val VERTICES_PER_BUFFER = 4
        private const val INDICES_PER_BUFFER = 6
    }

    private val shaderFactory = ShaderFactory.getInstance()

    private val mesh: Mesh
    private val vertices: FloatArray
    private val shaderProgram: ShaderProgram
    private lateinit var projectionMatrix: Matrix4

    private var active = false
    private var gpuCalls = 0

    private var bufferedCalls = 0
    private var cachedTexture: Texture? = null

    init {
        val maxVertices = VERTICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val maxIndices = INDICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val vertexAttributes = VertexAttributes(
                VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE)
        )

        mesh = Mesh(false, maxVertices, maxIndices, vertexAttributes)

        vertices = FloatArray(MAX_BUFFERED_CALLS * BUFFER_SIZE)
        val indices = ShortArray(MAX_BUFFERED_CALLS * INDICES_PER_BUFFER)

        // generate indices
        for (i in indices.indices) {
            val module = i % 6
            val idx = (i / 6) * 4
            if (module == 0 || module == 5) indices[i] = idx.toShort()
            if (module == 1) indices[i] = (idx + 1).toShort()
            if (module == 2 || module == 3) indices[i] = (idx + 2).toShort()
            if (module == 4) indices[i] = (idx + 3).toShort()
        }
        mesh.setIndices(indices)

        shaderProgram = shaderFactory.get("sprite", "sprite")
    }

    override fun begin() {
        if(active) throw RuntimeException("The renderer is already active")
        gpuCalls = 0
        shaderProgram.begin()
        active = true
    }

    override fun draw(drawable: Sprite) {
        if(drawable.textureRegion == null) throw RuntimeException("Sprite can not be rendered without texture")

        // flash control before drawing next drawable
        if (cachedTexture !== drawable.textureRegion!!.texture) {
            flush()
            cachedTexture = drawable.textureRegion!!.texture
        } else if(bufferedCalls == MAX_BUFFERED_CALLS) flush()

        appendVertices(drawable)
        bufferedCalls++
    }

    override fun end() {
        if(!active) throw RuntimeException("The renderer isn't active")
        if (bufferedCalls > 0) flush()
        cachedTexture = null
        active = false
        shaderProgram.end()
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4) {
        if (active) {
            flush()
        }
        this.projectionMatrix = projectionMatrix
    }

    private fun appendVertices(sprite: Sprite) {
        val idx = bufferedCalls * BUFFER_SIZE
        vertices[idx] = sprite.x
        vertices[idx + 1] = sprite.y
        vertices[idx + 2] = sprite.colorLeftBottom.toFloatBits()
        vertices[idx + 3] = sprite.textureRegion!!.u
        vertices[idx + 4] = sprite.textureRegion!!.v

        vertices[idx + 5] = sprite.x
        vertices[idx + 6] = sprite.y + sprite.textureRegion!!.regionHeight
        vertices[idx + 7] = sprite.colorLeftTop.toFloatBits()
        vertices[idx + 8] = sprite.textureRegion!!.u
        vertices[idx + 9] = sprite.textureRegion!!.v2

        vertices[idx + 10] = sprite.x + sprite.textureRegion!!.regionWidth
        vertices[idx + 11] = sprite.y + sprite.textureRegion!!.regionHeight
        vertices[idx + 12] = sprite.colorRightTop.toFloatBits()
        vertices[idx + 13] = sprite.textureRegion!!.u2
        vertices[idx + 14] = sprite.textureRegion!!.v2

        vertices[idx + 15] = sprite.x + sprite.textureRegion!!.regionWidth
        vertices[idx + 16] = sprite.y
        vertices[idx + 17] = sprite.colorRightBottom.toFloatBits()
        vertices[idx + 18] = sprite.textureRegion!!.u2
        vertices[idx + 19] = sprite.textureRegion!!.v
    }

    private fun flush() {
        if(bufferedCalls == 0) return

        gpuCalls++
        val indicesCount = bufferedCalls * INDICES_PER_BUFFER
        val idx = bufferedCalls * BUFFER_SIZE

        shaderProgram.setUniformMatrix("u_projectionTrans", projectionMatrix);
        shaderProgram.setUniformi("u_texture", 0);
        cachedTexture!!.bind()
        mesh.setVertices(vertices, 0, idx)
        mesh.indicesBuffer.position(0)
        mesh.indicesBuffer.limit(indicesCount)
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, indicesCount)
        bufferedCalls = 0
    }
}