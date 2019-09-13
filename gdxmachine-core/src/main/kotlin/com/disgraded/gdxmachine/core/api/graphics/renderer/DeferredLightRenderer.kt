package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class DeferredLightRenderer: Disposable {

    companion object {
        private const val BUFFER_SIZE = 24
        private const val VERTICES_PER_BUFFER = 4
        private const val INDICES_PER_BUFFER = 6
        private const val MAX_BUFFERED_CALLS = 1
    }

    private val shaderFactory = ShaderFactory.getInstance()

    private val mesh: Mesh
    private val vertices: FloatArray
    private val indices: ShortArray
    private var shaderProgram: ShaderProgram
    private val shaderVertexPrefix = "deferred.light"
    private var shaderFragmentPrefix = "deferred.light"

    init {
        val maxVertices = VERTICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val maxIndices = INDICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val vertexAttributes = VertexAttributes(
                VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "${ShaderProgram.TEXCOORD_ATTRIBUTE}_normal")
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

    fun render(ambientColor: Color, lightList: ArrayList<Light>, standardSnap: TextureRegion, normalSnap: TextureRegion,
               projectionMatrix: Matrix4): Int {
        shaderProgram.end()
        shaderProgram.begin()

        appendVertices(standardSnap, normalSnap)
        flush(ambientColor, lightList, standardSnap, normalSnap, projectionMatrix)

        shaderProgram.end()
        return 1
    }

    override fun dispose() = mesh.dispose()

    private fun appendVertices(standardSnap: TextureRegion, normalSnap: TextureRegion) {
        vertices[0] = 0 - standardSnap.regionWidth * .5f
        vertices[1] = 0 - standardSnap.regionHeight * .5f
        vertices[2] = standardSnap.u
        vertices[3] = standardSnap.v2
        vertices[4] = normalSnap.u
        vertices[5] = normalSnap.v2

        vertices[6] = vertices[0]
        vertices[7] = vertices[1] + standardSnap.regionHeight
        vertices[8] = standardSnap.u
        vertices[9] = standardSnap.v
        vertices[10] = normalSnap.u
        vertices[11] = normalSnap.v

        vertices[12] = vertices[0] + standardSnap.regionWidth
        vertices[13] = vertices[1] + standardSnap.regionHeight
        vertices[14] = standardSnap.u2
        vertices[15] = standardSnap.v
        vertices[16] = normalSnap.u2
        vertices[17] = normalSnap.v

        vertices[18] = vertices[0] + standardSnap.regionWidth
        vertices[19] = vertices[1]
        vertices[20] = standardSnap.u2
        vertices[21] = standardSnap.v2
        vertices[22] = normalSnap.u2
        vertices[23] = normalSnap.v2
    }

    private fun flush(ambientColor: Color, lightList: ArrayList<Light>,standardSnap: TextureRegion,
                      normalSnap: TextureRegion, projectionMatrix: Matrix4) {
        val indicesCount = MAX_BUFFERED_CALLS * INDICES_PER_BUFFER
        val verticesCount = MAX_BUFFERED_CALLS * BUFFER_SIZE

        standardSnap.texture.bind(0)
        normalSnap.texture.bind(1)

        val light = lightList[0]

        shaderProgram.setUniformMatrix("u_projectionTrans", projectionMatrix)
        shaderProgram.setUniformi("u_texture", 0)
        shaderProgram.setUniformi("u_texture_normal", 1)
        shaderProgram.setUniformf("light_position", light.x, light.y, light.z)
        shaderProgram.setUniformf("light_color", light.color.r, light.color.g, light.color.b, light.color.alpha)
        shaderProgram.setUniformf("resolution", 1280f, 720f)
        shaderProgram.setUniformf("ambient_color", ambientColor.r, ambientColor.g, ambientColor.b, ambientColor.alpha)
        shaderProgram.setUniformf("falloff", light.falloff.x, light.falloff.y, light.falloff.z)

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, indicesCount)
    }
}