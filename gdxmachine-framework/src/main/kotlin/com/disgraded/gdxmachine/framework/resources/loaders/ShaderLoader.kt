package com.disgraded.gdxmachine.framework.resources.loaders

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.disgraded.gdxmachine.framework.resources.assets.Shader
import java.io.BufferedReader

class ShaderLoader(resolver: FileHandleResolver) : AsynchronousAssetLoader<Shader, Shader.Parameters>(resolver) {

    private lateinit var vertex: String
    private lateinit var fragment: String

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: Shader.Parameters) {
        vertex = resolve(parameter.vertexPath).reader(parameter.encoding).buffered().use(BufferedReader::readText)
        fragment = resolve(parameter.fragmentPath).reader(parameter.encoding).buffered().use(BufferedReader::readText)
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: Shader.Parameters): Shader {
        val shader = Shader(vertex, fragment)
        shader.isCompiled
        return shader
    }

    override fun getDependencies(fileName: String, file: FileHandle, parameter: Shader.Parameters): Array<AssetDescriptor<Any>>? {
        return null
    }
}