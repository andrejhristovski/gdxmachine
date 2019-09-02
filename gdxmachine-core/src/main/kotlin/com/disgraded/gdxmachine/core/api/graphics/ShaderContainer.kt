package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class ShaderContainer {

    private val shaders = hashMapOf<String, ShaderProgram>()

    init {
        ShaderProgram.pedantic = false
        shaders[genKey(Drawable.Type.SPRITE, Drawable.Effect.TINT)] = compile("sprite", "sprite.tint")
        shaders[genKey(Drawable.Type.SPRITE, Drawable.Effect.FILL)] = compile("sprite", "sprite.fill")
        shaders[genKey(Drawable.Type.SPRITE, Drawable.Effect.INVERT)] = compile("sprite", "sprite.invert")
        shaders[genKey(Drawable.Type.SPRITE, Drawable.Effect.GREYSCALE)] = compile("sprite", "sprite.greyscale")
        shaders[genKey(Drawable.Type.SPRITE, Drawable.Effect.GREYSCALE_COLORED)] = compile("sprite", "sprite.greyscale_colored")
        shaders[genKey(Drawable.Type.SPRITE, Drawable.Effect.SEPIA)] = compile("sprite", "sprite.sepia")
    }

    fun get(type: Drawable.Type, effect: Drawable.Effect): ShaderProgram {
        return shaders[genKey(type, effect)]!!
    }

    private fun genKey(type: Drawable.Type, effect: Drawable.Effect): String {
        return "${type.key}:${effect.key}"
    }

    private fun compile(vertex: String, fragment: String): ShaderProgram {
        val vertexSource = Gdx.files.classpath("shaders/vertex/$vertex.vertex.glsl").readString()
        val fragmentSource = Gdx.files.classpath("shaders/fragment/$fragment.fragment.glsl").readString()
        val shader = ShaderProgram(vertexSource, fragmentSource)
        require(shader.isCompiled) { "Error compiling shader: " + shader.log }
        return shader
    }

}
