package com.disgraded.gdxmachine.core.api.graphics.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.Projection
import com.disgraded.gdxmachine.core.api.graphics.ShaderFactory
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.utils.Color

class DeferredLightingRenderer(private val projection: Projection) {

    private val shaderFactory = ShaderFactory.getInstance()

    private val mesh: Mesh
    private val vertices: FloatArray
    private val indices: ShortArray
    private val shaderProgram: ShaderProgram
    private val shaderVertexPrefix = "deferred_lighting"
    private val shaderFragmentPrefix = "deferred_lighting"

    private lateinit var diffuse: TextureRegion
    private lateinit var bump: TextureRegion

    private lateinit var ambientLight: Color
    private lateinit var lightList: ArrayList<Light>
    private lateinit var projectionMatrix: Matrix4

    init {
        val vertexAttributes = VertexAttributes(
                VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "${ShaderProgram.COLOR_ATTRIBUTE}_ambient"),
                VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE)
        )

        mesh = Mesh(false, 4, 6, vertexAttributes)
        vertices = FloatArray(20)
        indices = ShortArray(6)
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

    fun render(diffuse: TextureRegion, bump: TextureRegion, ambientLight: Color, lightList: ArrayList<Light>,
               projectionMatrix: Matrix4): Int {
        this.diffuse = diffuse
        this.bump = bump
        this.ambientLight = ambientLight
        this.projectionMatrix = projectionMatrix
        this.lightList = lightList
        appendVertices()
        flush()
        return 1
    }

    private fun appendVertices() {
        val sizeX = diffuse.regionWidth
        val sizeY = diffuse.regionHeight
        val x = projection.camera.position.x
        val y = projection.camera.position.y


        var x1 = 0f - sizeX
        var y1 = 0f - sizeY
        var x2 = x1
        var y2 = y1 + sizeY
        var x3 = x1 + sizeX
        var y3 = y1 + sizeY
        var x4 = x1 + sizeX
        var y4 = y1

        x1 += x + sizeX / 2
        x2 += x + sizeX / 2
        x3 += x + sizeX / 2
        x4 += x + sizeX / 2
        y1 += y + sizeY / 2
        y2 += y + sizeY / 2
        y3 += y + sizeY / 2
        y4 += y + sizeY / 2

        val idx = 0
        vertices[idx] = x1
        vertices[idx + 1] = y1
        vertices[idx + 2] = ambientLight.toFloatBits()
        vertices[idx + 3] = diffuse.u
        vertices[idx + 4] = diffuse.v2

        vertices[idx + 5] = x2
        vertices[idx + 6] = y2
        vertices[idx + 7] = ambientLight.toFloatBits()
        vertices[idx + 8] = diffuse.u
        vertices[idx + 9] = diffuse.v

        vertices[idx + 10] = x3
        vertices[idx + 11] = y3
        vertices[idx + 12] = ambientLight.toFloatBits()
        vertices[idx + 13] = diffuse.u2
        vertices[idx + 14] = diffuse.v

        vertices[idx + 15] = x4
        vertices[idx + 16] = y4
        vertices[idx + 17] = ambientLight.toFloatBits()
        vertices[idx + 18] = diffuse.u2
        vertices[idx + 19] = diffuse.v2
    }

    private fun flush() {
        shaderProgram.begin()
        val indicesCount = 6
        val verticesCount = 20

        diffuse.texture.bind(0)
        bump.texture.bind(1)

        shaderProgram.setUniformMatrix("u_projectionTrans", projectionMatrix)
        shaderProgram.setUniformi("u_texture_diffuse", 0)
        shaderProgram.setUniformi("u_texture_bump", 1)
        shaderProgram.setUniformf("ambient_light_color", ambientLight.r, ambientLight.g, ambientLight.b,
                ambientLight.a)
        for (i in 0 until lightList.size) {
            val light = lightList[i]
            val positionId = shaderProgram.getUniformLocation("light[$i].position")
            val colorId = shaderProgram.getUniformLocation("light[$i].color")
            shaderProgram.setUniformf(positionId, light.x, light.y, light.z)
            shaderProgram.setUniformf(colorId, light.color.r, light.color.g, light.color.b, light.color.a)
        }

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, indicesCount)
        shaderProgram.end()
        Gdx.gl20.glActiveTexture(0)
    }

}