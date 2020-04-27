package com.disgraded.gdxmachine.framework.batches

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.graphics.VertexAttribute as Attribute
import com.badlogic.gdx.graphics.VertexAttributes as Attributes
import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.graphics.Batch
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.utils.Color
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.graphics.utils.Transform2D
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.utils.Corner

class SpriteBatch(private val mode: Mode = Mode.DIFFUSE) : Batch {

    enum class Mode {
        DIFFUSE, BUMP
    }

    private val bufferSize = 28
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
    private var cachedTexture: Texture
    private var cachedMask: Texture

    private var noTexture: TextureRegion
    private var noMask: TextureRegion
    private var noNormal: TextureRegion
    private var textureExists = 0

    private val tempColor = Color("#ffffff")

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
        append(sprite, sprite.absolute, texture, mask)
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

    private fun append(sprite: Sprite, transform2D: Transform2D, texture: TextureRegion, mask: TextureRegion) {
        val idx = bufferedCalls * bufferSize

        val sizeX = texture.regionWidth * transform2D.scaleX
        val sizeY = texture.regionHeight * transform2D.scaleY

        var x1 = 0 - (sizeX * transform2D.anchorX)
        var y1 = 0 - (sizeY * transform2D.anchorY)
        var x2 = x1
        var y2 = y1 + sizeY
        var x3 = x1 + sizeX
        var y3 = y1 + sizeY
        var x4 = x1 + sizeX
        var y4 = y1

        if (transform2D.angle != 0f) {
            val cos = MathUtils.cosDeg(transform2D.angle)
            val sin = MathUtils.sinDeg(transform2D.angle)
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

        x1 += transform2D.x
        x2 += transform2D.x
        x3 += transform2D.x
        x4 += transform2D.x
        y1 += transform2D.y
        y2 += transform2D.y
        y3 += transform2D.y
        y4 += transform2D.y

        tempColor.set(sprite.getColor(Corner.BOTTOM_LEFT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx] = x1
        vertices[idx + 1] = y1
        vertices[idx + 2] = tempColor.getBits()
        vertices[idx + 3] = texture.u
        vertices[idx + 4] = texture.v2
        vertices[idx + 5] = mask.u
        vertices[idx + 6] = mask.v2

        tempColor.set(sprite.getColor(Corner.TOP_LEFT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx + 7] = x2
        vertices[idx + 8] = y2
        vertices[idx + 9] = tempColor.getBits()
        vertices[idx + 10] = texture.u
        vertices[idx + 11] = texture.v
        vertices[idx + 12] = mask.u
        vertices[idx + 13] = mask.v

        tempColor.set(sprite.getColor(Corner.TOP_RIGHT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx + 14] = x3
        vertices[idx + 15] = y3
        vertices[idx + 16] = tempColor.getBits()
        vertices[idx + 17] = texture.u2
        vertices[idx + 18] = texture.v
        vertices[idx + 19] = mask.u2
        vertices[idx + 20] = mask.v

        tempColor.set(sprite.getColor(Corner.BOTTOM_RIGHT))
        tempColor.setOpacity(sprite.opacity)
        vertices[idx + 21] = x4
        vertices[idx + 22] = y4
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
        val verticesCount = bufferedCalls * bufferSize
        val indicesCount = bufferedCalls * indicesPerBuffer

        shader.begin()
        cachedTexture.bind(0)
        cachedMask.bind(1)
        shader.setUniformMatrix("u_projectionTrans", projectionMatrix)
        shader.setUniformi("u_texture", 0)
        shader.setUniformi("u_texture_mask", 1)
        shader.setUniformi("u_texture_exists", textureExists)

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