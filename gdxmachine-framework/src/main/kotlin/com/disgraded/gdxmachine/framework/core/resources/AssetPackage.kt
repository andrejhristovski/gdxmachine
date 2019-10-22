package com.disgraded.gdxmachine.framework.core.resources

import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.framework.core.Core
import com.disgraded.gdxmachine.framework.core.resources.assets.PlainText
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData
import com.disgraded.gdxmachine.framework.core.resources.loaders.PlainTextLoader
import com.disgraded.gdxmachine.framework.core.resources.loaders.ShaderLoader
import kotlin.reflect.KClass

abstract class AssetPackage(val key: String, fileHandleResolver: FileHandleResolver = defaultResolver): Disposable {

    companion object {
        private val defaultResolver = InternalFileHandleResolver()
    }

    protected val core = Core

    private val assetManager = AssetManager(fileHandleResolver)
    private val keyMap = hashMapOf<String, String>()
    private var markedAsDone = false

    val diagnostics: String
        get() = assetManager.diagnostics

    val assetKeys: MutableSet<String>
        get() = keyMap.keys

    val done: Boolean
        get() = assetManager.isFinished

    val loaded: Int
        get() = assetManager.loadedAssets

    val progress: Float
        get() = assetManager.progress

    val queued: Int
        get() = assetManager.queuedAssets

    init {
        assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(fileHandleResolver))
        assetManager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(fileHandleResolver))
        assetManager.setLoader(PlainText::class.java, PlainTextLoader(fileHandleResolver))
        assetManager.setLoader(ShaderData::class.java, ShaderLoader(fileHandleResolver))
    }

    open fun onComplete() {}

    fun <T: Any> get(key: String): T {
        if (!keyMap.containsKey(key)) throw RuntimeException("")
        return assetManager.get<T>(keyMap[key])
    }

    fun contains(key: String, type: KClass<Any>? = null): Boolean {
        if (!keyMap.containsKey(key)) return false
        if (type != null) {
            return assetManager.contains(keyMap[key], type.java)
        } else {
            return assetManager.contains(keyMap[key])
        }
    }

    fun <T: Any> contains(asset: T): Boolean = assetManager.containsAsset(asset)

    fun isLoaded(key: String, type: KClass<Any>? = null): Boolean {
        if (!keyMap.containsKey(key)) return false
        if (type != null) {
            return assetManager.isLoaded(keyMap[key], type.java)
        } else {
            return assetManager.isLoaded(keyMap[key])
        }
    }

    fun <T: Any> load(key: String, path: String, type: KClass<T>, parameter: AssetLoaderParameters<T>? = null) {
        if (keyMap.containsKey(key)) throw RuntimeException("") // TODO: MESSAGE HERE
        if (parameter !== null) {
            assetManager.load(path, type.java, parameter)
        } else {
            assetManager.load(path, type.java)
        }
        keyMap[key] = path
    }

    fun unload(key: String) {
        if (!keyMap.containsKey(key)) throw RuntimeException("")  // TODO: MESSAGE HERE
        assetManager.unload(keyMap[key])
    }

    fun proceed(ms: Int = 0) {
        if (ms == 0) assetManager.update()
        else assetManager.update(ms)
    }

    fun sync(markOnly: Boolean = false) {
        if (!done && !markOnly) {
            assetManager.finishLoading()
        }
        if (!markedAsDone) {
            markedAsDone = true
            onComplete()
        }
    }

    fun clear() = assetManager.clear()

    override fun dispose() {
        assetManager.clear()
        assetManager.dispose()
    }
}
