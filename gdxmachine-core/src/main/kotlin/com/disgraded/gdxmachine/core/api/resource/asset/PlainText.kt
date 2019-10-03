package com.disgraded.gdxmachine.core.api.resource.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import java.io.BufferedReader

data class PlainText(val text: String)

data class PlainTextLoaderParams(val fileName: String) : AssetLoaderParameters<PlainText>() {
    val encoding = "UTF-8"
}

class PlainTextLoader(resolver: FileHandleResolver) : AsynchronousAssetLoader<PlainText, PlainTextLoaderParams>(resolver) {
    data class TextData(val text: String)

    var data: TextData = TextData("")

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: PlainTextLoaderParams) {
        val text = file.reader(parameter.encoding).buffered().use(BufferedReader::readText)
        data = TextData(text)
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: PlainTextLoaderParams): PlainText {
        return PlainText(data.text)
    }

    override fun getDependencies(fileName: String, file: FileHandle, parameter: PlainTextLoaderParams): Array<AssetDescriptor<Any>>? {
        return null
    }
}