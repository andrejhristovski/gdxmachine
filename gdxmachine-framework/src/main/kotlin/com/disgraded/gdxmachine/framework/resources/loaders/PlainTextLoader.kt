package com.disgraded.gdxmachine.framework.resources.loaders

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.disgraded.gdxmachine.framework.resources.assets.PlainText
import java.io.BufferedReader

class PlainTextLoader(resolver: FileHandleResolver)
    : AsynchronousAssetLoader<PlainText, PlainText.Parameters>(resolver) {

    private lateinit var text: String

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle,
                           parameter: PlainText.Parameters) {
        text = file.reader(parameter.encoding).buffered().use(BufferedReader::readText)
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle,
                          parameter: PlainText.Parameters): PlainText {
        return PlainText(text)
    }

    override fun getDependencies(fileName: String, file: FileHandle,
                          parameter: PlainText.Parameters): Array<AssetDescriptor<Any>>? {
        return null
    }
}