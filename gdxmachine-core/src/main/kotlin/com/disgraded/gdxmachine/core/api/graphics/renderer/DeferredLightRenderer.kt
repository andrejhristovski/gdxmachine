package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class DeferredLightRenderer {

    companion object {
        private const val BUFFER_SIZE = 28
        private const val VERTICES_PER_BUFFER = 4
        private const val INDICES_PER_BUFFER = 6
        private const val MAX_BUFFERED_CALLS = 1
    }

    private val shaderFactory = ShaderFactory.getInstance()

    private val mesh: Mesh
    private val vertices: FloatArray
    private val indices: ShortArray
    private val shaderProgram: ShaderProgram

    private var bufferedCalls = 0

    init {
        val maxVertices = VERTICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val maxIndices = INDICES_PER_BUFFER * MAX_BUFFERED_CALLS
        val vertexAttributes = VertexAttributes(
                VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
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

        shaderProgram = shaderFactory.get("deferred", "deferred")
    }

    fun render(projectionMatrix: Matrix4, sceneTexture: Texture, sceneNormalMapTexture: Texture) {
        bufferedCalls = 0
        shaderProgram.begin()
        appendVertices(sceneTexture, sceneNormalMapTexture)

        val indicesCount = bufferedCalls * INDICES_PER_BUFFER
        val verticesCount = bufferedCalls * BUFFER_SIZE
        sceneTexture.bind(0)
        sceneNormalMapTexture.bind(1)
        Gdx.gl.glActiveTexture(0)

        val lp = Vector3(.5f, .5f, 0.075f)
        val lc = Color.WARM_WHITE
        val ac = Color.BLACK
        val falloff = Vector3(.4f, 3f, 20f)

        shaderProgram.setUniformMatrix("u_projectionTrans", projectionMatrix)
        shaderProgram.setUniformi("u_texture", 0)
        shaderProgram.setUniformi("u_texture_normal", 1)
        shaderProgram.setUniformf("light_position", lp.x, lp.y, lp.z)
        shaderProgram.setUniformf("light_color", lc.r, lc.g, lc.b, lc.alpha)
        shaderProgram.setUniformf("ambient_color", ac.r, ac.g, ac.b, ac.alpha)
        shaderProgram.setUniformf("resolution", 1280f, 720f)
        shaderProgram.setUniformf("falloff", falloff.x, falloff.y, falloff.z)

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, indicesCount)
        shaderProgram.end()
    }

    private fun appendVertices(sceneTexture: Texture, sceneNormalMapTexture: Texture) {
        val texRegionScene = TextureRegion(sceneTexture)
        val texRegionNormal = TextureRegion(sceneNormalMapTexture)
        texRegionScene.flip(false, true)
        texRegionNormal.flip(false, true)

        val idx = bufferedCalls * BUFFER_SIZE
        val sizeX = sceneTexture.width
        val sizeY = sceneTexture.height

        vertices[idx] = sizeX * -.5f
        vertices[idx + 1] = sizeY * -.5f
        vertices[idx + 2] = Color.WHITE.toFloatBits()
        vertices[idx + 3] = texRegionScene.u
        vertices[idx + 4] = texRegionScene.v2
        vertices[idx + 5] = texRegionNormal.u
        vertices[idx + 6] = texRegionNormal.v2

        vertices[idx + 7] = vertices[idx]
        vertices[idx + 8] = vertices[idx + 1]  + sizeY
        vertices[idx + 9] = Color.WHITE.toFloatBits()
        vertices[idx + 10] = texRegionScene.u
        vertices[idx + 11] = texRegionScene.v
        vertices[idx + 12] = texRegionNormal.u
        vertices[idx + 13] = texRegionNormal.v

        vertices[idx + 14] = vertices[idx] + sizeX
        vertices[idx + 15] = vertices[idx + 1] + sizeY
        vertices[idx + 16] = Color.WHITE.toFloatBits()
        vertices[idx + 17] = texRegionScene.u2
        vertices[idx + 18] = texRegionScene.v
        vertices[idx + 19] = texRegionNormal.u2
        vertices[idx + 20] = texRegionNormal.v

        vertices[idx + 21] = vertices[idx] + sizeX
        vertices[idx + 22] = vertices[idx + 1]
        vertices[idx + 23] = Color.WHITE.toFloatBits()
        vertices[idx + 24] = texRegionScene.u2
        vertices[idx + 25] = texRegionScene.v2
        vertices[idx + 26] = texRegionNormal.u2
        vertices[idx + 27] = texRegionNormal.v2
        bufferedCalls++
    }
}