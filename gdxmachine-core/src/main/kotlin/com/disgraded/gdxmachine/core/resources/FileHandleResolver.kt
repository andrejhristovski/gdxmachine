package com.disgraded.gdxmachine.core.resources

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.FileHandleResolver as GdxFileHandleResolver
import com.badlogic.gdx.files.FileHandle

class FileHandleResolver : GdxFileHandleResolver {
    override fun resolve(fileName: String?): FileHandle {
        return Gdx.files.internal(fileName)
    }
}