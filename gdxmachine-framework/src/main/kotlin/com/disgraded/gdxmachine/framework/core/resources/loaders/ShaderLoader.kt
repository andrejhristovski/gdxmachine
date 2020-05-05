package com.disgraded.gdxmachine.framework.core.resources.loaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData
import java.io.BufferedReader

class ShaderLoader(resolver: FileHandleResolver) : AsynchronousAssetLoader<ShaderData, ShaderData.Parameters>(resolver) {

    private lateinit var content: String

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: ShaderData.Parameters?) {
        content = file.readString()
        println("content:, $content")
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: ShaderData.Parameters?): ShaderData {
        return ShaderData(content)
    }

    override fun getDependencies(fileName: String, file: FileHandle, parameter: ShaderData.Parameters?): Array<AssetDescriptor<Any>>? {
        return null
    }
}