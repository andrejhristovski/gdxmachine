package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.Projection
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Corner
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.PointLight

class LightAttenuationPointRenderer(private val projection: Projection) : Renderer {

    companion object {
        private const val BUFFER_SIZE = 28
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
    private val shaderProgram: ShaderProgram
    private val shaderVertexPrefix = "light_attenuation_point"
    private val shaderFragmentPrefix = "light_attenuation_point"
    private lateinit var projectionMatrix: Matrix4

    init {
        val maxVertices = VERTICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val maxIndices = INDICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val vertexAttributes = VertexAttributes(
                VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.Position, 2, "a_size"),
                VertexAttribute(VertexAttributes.Usage.Position, 2, "${ShaderProgram.POSITION_ATTRIBUTE}_relative"),
                VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_generic_intensity")
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

    override fun start() {
        if(active) throw RuntimeException("The renderer is already active")
        gpuCalls = 0
        shaderProgram.begin()
        active = true
    }

    override fun draw(drawable: Drawable) {
        val pointLight = drawable as PointLight
        appendVertices(pointLight)
    }

    override fun finish(): Int {
        if(!active) throw RuntimeException("The renderer isn't active")
        if (bufferedCalls > 0) flush()
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

    private fun appendVertices(pointLight: PointLight) {
        val idx = bufferedCalls * BUFFER_SIZE
        val size = projection.getVirtualWidth() / 5
        val sizeX = size * pointLight.intensity * 4
        val sizeY = size * pointLight.intensity * 4

        var x1 = 0 - (sizeX * .5f)
        var y1 = 0 - (sizeY * .5f)
        var x2 = x1
        var y2 = y1 + sizeY
        var x3 = x1 + sizeX
        var y3 = y1 + sizeY
        var x4 = x1 + sizeX
        var y4 = y1

        if (pointLight.angle != 0f) {
            val cos = MathUtils.cosDeg(pointLight.angle)
            val sin = MathUtils.sinDeg(pointLight.angle)
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

        x1 += pointLight.x
        x2 += pointLight.x
        x3 += pointLight.x
        x4 += pointLight.x
        y1 += pointLight.y
        y2 += pointLight.y
        y3 += pointLight.y
        y4 += pointLight.y

        vertices[idx] = x1
        vertices[idx + 1] = y1
        vertices[idx + 2] = sizeX * pointLight.scaleX
        vertices[idx + 3] = sizeY * pointLight.scaleY
        vertices[idx + 4] = x1 - pointLight.x
        vertices[idx + 5] = y1 - pointLight.y
        vertices[idx + 6] = pointLight.intensity

        vertices[idx + 7] = x2
        vertices[idx + 8] = y2
        vertices[idx + 9] = sizeX * pointLight.scaleX
        vertices[idx + 10] = sizeY * pointLight.scaleY
        vertices[idx + 11] = x2 - pointLight.x
        vertices[idx + 12] = y2 - pointLight.y
        vertices[idx + 13] = pointLight.intensity

        vertices[idx + 14] = x3
        vertices[idx + 15] = y3
        vertices[idx + 16] = sizeX * pointLight.scaleX
        vertices[idx + 17] = sizeY * pointLight.scaleY
        vertices[idx + 18] = x3 - pointLight.x
        vertices[idx + 19] = y3 - pointLight.y
        vertices[idx + 20] = pointLight.intensity

        vertices[idx + 21] = x4
        vertices[idx + 22] = y4
        vertices[idx + 23] = sizeX * pointLight.scaleX
        vertices[idx + 24] = sizeY * pointLight.scaleY
        vertices[idx + 25] = x4 - pointLight.x
        vertices[idx + 26] = y4 - pointLight.y
        vertices[idx + 27] = pointLight.intensity
        bufferedCalls++
    }

    private fun flush() {
        if(bufferedCalls == 0) return
        gpuCalls++

        val indicesCount = bufferedCalls * INDICES_PER_BUFFER
        val verticesCount = bufferedCalls * BUFFER_SIZE

        shaderProgram.setUniformMatrix("u_projectionTrans", projectionMatrix)

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glEnable(GL20.GL_BLEND_EQUATION)
        Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD)
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, indicesCount)
        bufferedCalls = 0
    }
}