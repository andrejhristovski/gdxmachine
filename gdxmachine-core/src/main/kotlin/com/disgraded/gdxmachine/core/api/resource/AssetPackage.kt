package com.disgraded.gdxmachine.core.api.resource

import com.badlogic.gdx.assets.loaders.BitmapFontLoader
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.assets.AssetManager as GdxAssetManager
import com.badlogic.gdx.utils.Disposable

open class AssetPackage(val packageKey: String) : Disposable {

    private val gdxAssetManager = GdxAssetManager(InternalFileHandleResolver(), true)
    private val assetKeys = hashMapOf<String, String>()

    init {
        gdxAssetManager.setLoader(FreeTypeFontGenerator::class.java,
                FreeTypeFontGeneratorLoader(gdxAssetManager.fileHandleResolver))
        gdxAssetManager.setLoader(BitmapFont::class.java, ".ttf",
                FreetypeFontLoader(gdxAssetManager.fileHandleResolver))
    }

    protected fun loadTexture(key: String, path: String, format: Pixmap.Format? = null, genMipMaps: Boolean = false,
                    minFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest,
                    magFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest,
                    wrapU: Texture.TextureWrap = Texture.TextureWrap.ClampToEdge,
                    wrapV: Texture.TextureWrap = Texture.TextureWrap.ClampToEdge
    ) {
        val params = TextureLoader.TextureParameter()
        params.format = format
        params.genMipMaps = genMipMaps
        params.minFilter = minFilter
        params.magFilter = magFilter
        params.wrapU = wrapU
        params.wrapV = wrapV
        gdxAssetManager.load(path, Texture::class.java, params)
        assetKeys[key] = path
    }

    protected fun loadPixelMap(key: String, path: String) {
        gdxAssetManager.load(path, Pixmap::class.java)
        assetKeys[key] = path
    }

    protected fun loadBitmapFont(key: String, path: String, flip: Boolean = false, genMipMaps: Boolean = false,
                       minFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest,
                       magFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest) {
        val params = BitmapFontLoader.BitmapFontParameter()
        params.flip = flip
        params.genMipMaps = genMipMaps
        params.minFilter = minFilter
        params.magFilter = magFilter
        gdxAssetManager.load(path, BitmapFont::class.java, params)
        assetKeys[key] = path
    }

    protected fun loadFreeTypeFont(key: String, path: String, fontParams: FreeTypeFontGenerator.FreeTypeFontParameter
    = FreeTypeFontGenerator.FreeTypeFontParameter()) {
        val params = FreetypeFontLoader.FreeTypeFontLoaderParameter()
        params.fontFileName = path
        params.fontParameters = fontParams
        gdxAssetManager.load(path, BitmapFont::class.java, params)
        assetKeys[key] = path
    }

    protected fun loadTextureAtlas(key: String, path: String, flip: Boolean = false) {
        val params = TextureAtlasLoader.TextureAtlasParameter()
        params.flip = flip
        gdxAssetManager.load(path, TextureAtlas::class.java, params)
        assetKeys[key] = path
    }

    protected fun loadMusic(key: String, path: String) {
        gdxAssetManager.load(path, Music::class.java)
        assetKeys[key] = path
    }

    protected fun loadSound(key: String, path: String) {
        gdxAssetManager.load(path, Sound::class.java)
        assetKeys[key] = path
    }

    fun update(): Boolean {
        return gdxAssetManager.update()
    }

    fun sync() {
        gdxAssetManager.finishLoading()
    }

    fun <T> get(key: String): T {
        if (assetKeys.containsKey(key)) {
            return gdxAssetManager.get(assetKeys[key])
        } else {
            throw RuntimeException("Asset named as \"$key\" not found in the package [ $packageKey ]")
        }
    }

    fun getProgress(): Float {
        return gdxAssetManager.progress
    }

    override fun dispose() {
        gdxAssetManager.clear()
        assetKeys.clear()
    }
}