package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.core.graphics.utils.Mesh2D
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.graphics.utils.Transform2D
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.utils.Corner
import com.badlogic.gdx.graphics.VertexAttribute as Attribute
import com.badlogic.gdx.graphics.VertexAttributes as Attributes

class SpriteBatch(private val mode: Mode = Mode.DIFFUSE) : Batch {

    enum class Mode {
        DIFFUSE, BUMP
    }

    private val bufferSize = 28
    private val verticesPerBuffer = 4
    private val indicesPerBuffer = 6
    private var idx = 0
    private val maxCalls = Short.MAX_VALUE / verticesPerBuffer
    private var bufferedCalls = 0
    private var gpuCalls = 0

    private val mesh: Mesh
    private val vertices: FloatArray
    private val indices: ShortArray

    private var verticesCount = 0
    private var indicesCount = 0
    private val mesh2d = Mesh2D()

    private lateinit var projectionMatrix: Matrix4
    private val defaultShader: Shader
    private var shader: Shader
    private var cachedTexture: Texture
    private var cachedMask: Texture

    private var noTexture: TextureRegion
    private var noMask: TextureRegion
    private var noNormal: TextureRegion
    private var textureExists = 0

    private val tempColor = Color("#ffffff")

    private val blendEquation: Int
    private val blendSrcRGB: Int
    private val blendDstRGB: Int
    private val blendSrcAlpha: Int
    private val blendDstAlpha: Int

    init {
        val maxVertices = verticesPerBuffer * maxCalls
        val maxIndices = indicesPerBuffer * maxCalls
        val vertexAttributes = Attributes(
                Attribute(Attributes.Usage.Position, 2, Shader.POSITION(0)),
                Attribute(Attributes.Usage.ColorPacked, 4, Shader.COLOR(0)),
                Attribute(Attributes.Usage.TextureCoordinates, 2, Shader.TEXCOORD(0)),
                Attribute(Attributes.Usage.TextureCoordinates, 2, Shader.TEXCOORD(1))
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
        val shaderName = when(mode) {
            Mode.DIFFUSE -> "sprite.tint"
            Mode.BUMP -> "sprite.bump"
        }
        defaultShader = Core.graphics.getShader(shaderName)
        shader = defaultShader

        val texture = Core.resources.get("engine").get<Texture>("no_image")
        val mask = Core.resources.get("engine").get<Texture>("no_mask")
        val normal = Core.resources.get("engine").get<Texture>("no_normal")

        cachedTexture = texture
        cachedMask = texture


        noTexture = TextureRegion(texture)
        noMask = TextureRegion(mask)
        noNormal = TextureRegion(normal)

        when(mode) {
            Mode.DIFFUSE -> {
                blendEquation = GL20.GL_FUNC_ADD
                blendSrcRGB = GL20.GL_SRC_ALPHA
                blendDstRGB = GL20.GL_ONE_MINUS_SRC_ALPHA
                blendSrcAlpha = GL20.GL_SRC_ALPHA
                blendDstAlpha = GL20.GL_ONE_MINUS_SRC_ALPHA
            }
            Mode.BUMP -> {
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
        val sprite = drawable as Sprite
        if (!sprite.isUpdated()) sprite.update()

        if (mode == Mode.DIFFUSE) {
            checkShader(sprite.shader)
        }

        val texture = when(mode) {
            Mode.DIFFUSE -> sprite.getTexture(Sprite.DIFFUSE_TEXTURE) ?: noTexture
            Mode.BUMP -> sprite.getTexture(Sprite.NORMAL_TEXTURE) ?: noNormal
        }

        val mask = when(mode) {
            Mode.DIFFUSE -> sprite.getTexture(Sprite.MASK_TEXTURE) ?: noMask
            Mode.BUMP -> sprite.getTexture(Sprite.MASK_TEXTURE) ?: sprite.getTexture(Sprite.DIFFUSE_TEXTURE) ?: noMask
        }

        textureExists = if (sprite.getTexture(Sprite.DIFFUSE_TEXTURE) != null) 1 else 0

        checkTextures(texture, mask)

        if (bufferedCalls >= maxCalls) {
            flush()
        }
        append(sprite.absolute, sprite, texture, mask)
    }

    override fun end(): Int {
        if (bufferedCalls > 0) {
            flush()
        }
        val totalGpuCalls = gpuCalls
        gpuCalls = 0
        return  totalGpuCalls
    }

    override fun dispose() = mesh.dispose()

    private fun checkShader(shader: Shader?) {
        if (shader == null) {
            if (this.shader != defaultShader) {
                flush()
                this.shader = defaultShader
            }
        } else {
            if (this.shader != shader) {
                flush()
                this.shader = shader
            }
        }
    }

    private fun checkTextures(texture: TextureRegion, mask: TextureRegion) {
        if (cachedTexture != texture.texture || cachedMask != mask.texture) {
            flush()
            cachedTexture = texture.texture
            cachedMask = mask.texture
        }
    }

    private fun append(transform2D: Transform2D, sprite: Sprite, texture: TextureRegion, mask: TextureRegion) {
        idx = bufferedCalls * bufferSize
        mesh2d.set(transform2D, texture.regionWidth, texture.regionHeight)

        tempColor.set(sprite.getColor(Corner.BOTTOM_LEFT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx] = mesh2d.x1
        vertices[idx + 1] = mesh2d.y1
        vertices[idx + 2] = tempColor.getBits()
        vertices[idx + 3] = texture.u
        vertices[idx + 4] = texture.v2
        vertices[idx + 5] = mask.u
        vertices[idx + 6] = mask.v2

        tempColor.set(sprite.getColor(Corner.TOP_LEFT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx + 7] = mesh2d.x2
        vertices[idx + 8] = mesh2d.y2
        vertices[idx + 9] = tempColor.getBits()
        vertices[idx + 10] = texture.u
        vertices[idx + 11] = texture.v
        vertices[idx + 12] = mask.u
        vertices[idx + 13] = mask.v

        tempColor.set(sprite.getColor(Corner.TOP_RIGHT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx + 14] = mesh2d.x3
        vertices[idx + 15] = mesh2d.y3
        vertices[idx + 16] = tempColor.getBits()
        vertices[idx + 17] = texture.u2
        vertices[idx + 18] = texture.v
        vertices[idx + 19] = mask.u2
        vertices[idx + 20] = mask.v

        tempColor.set(sprite.getColor(Corner.BOTTOM_RIGHT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx + 21] = mesh2d.x4
        vertices[idx + 22] = mesh2d.y4
        vertices[idx + 23] = tempColor.getBits()
        vertices[idx + 24] = texture.u2
        vertices[idx + 25] = texture.v2
        vertices[idx + 26] = mask.u2
        vertices[idx + 27] = mask.v2
        bufferedCalls++
    }

    private fun flush() {
        if (bufferedCalls == 0) return
        gpuCalls++
        verticesCount = bufferedCalls * bufferSize
        indicesCount = bufferedCalls * indicesPerBuffer

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendEquation(blendEquation)
        Gdx.gl.glBlendFuncSeparate(blendSrcRGB, blendDstRGB, blendSrcAlpha, blendDstAlpha)


        shader.begin()
        cachedTexture.bind(0)
        cachedMask.bind(1)
        shader.setUniformMatrix("u_projectionTrans", projectionMatrix)
        shader.setUniformi("u_texture", 0)
        shader.setUniformi("u_texture_mask", 1)
        shader.setUniformi("u_texture_exists", textureExists)

        mesh.setVertices(vertices, 0, verticesCount)
        mesh.setIndices(indices, 0, indicesCount)


        mesh.render(shader, GL20.GL_TRIANGLES, 0, indicesCount)
        shader.end()
        bufferedCalls = 0
    }
}