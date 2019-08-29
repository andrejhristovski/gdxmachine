package com.disgraded.gdxmachine.core.resources

import com.badlogic.gdx.assets.loaders.*
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.assets.AssetManager as GdxAssetManager

open class AssetPackage(val key: String): Disposable {

    private val manager = GdxAssetManager(FileHandleResolver(), true)
    private val keys: HashMap<String, String> = hashMapOf()

    init {
        manager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(manager.fileHandleResolver))
        manager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(manager.fileHandleResolver))
    }

    fun loadTexture(key: String, path: String, format: Pixmap.Format? = null, genMipMaps: Boolean = false,
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
        manager.load(path, Texture::class.java, params)
        keys[key] = path
    }

    fun loadPixelMap(key: String, path: String) {
        manager.load(path, Pixmap::class.java)
        keys[key] = path
    }

    fun loadBitmapFont(key: String, path: String, flip: Boolean = false, genMipMaps: Boolean = false,
                       minFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest,
                       magFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest) {
        val params = BitmapFontLoader.BitmapFontParameter()
        params.flip = flip
        params.genMipMaps = genMipMaps
        params.minFilter = minFilter
        params.magFilter = magFilter
        manager.load(path, BitmapFont::class.java, params)
        keys[key] = path
    }

    fun loadFreeTypeFont(key: String, path: String, fontParams: FreeTypeFontGenerator.FreeTypeFontParameter
                         = FreeTypeFontGenerator.FreeTypeFontParameter()) {
        val params = FreetypeFontLoader.FreeTypeFontLoaderParameter()
        params.fontFileName = path
        params.fontParameters = fontParams
        manager.load(path, BitmapFont::class.java, params)
        keys[key] = path
    }

    fun loadTextureAtlas(key: String, path: String, flip: Boolean = false) {
        val params = TextureAtlasLoader.TextureAtlasParameter()
        params.flip = flip
        manager.load(path, TextureAtlas::class.java, params)
        keys[key] = path
    }

    fun loadMusic(key: String, path: String) {
        manager.load(path, Music::class.java)
        keys[key] = path
    }

    fun loadSound(key: String, path: String) {
        manager.load(path, Sound::class.java)
        keys[key] = path
    }

    fun update(): Boolean {
        return manager.update()
    }

    fun instant() {
        manager.finishLoading()
    }

    fun <T> get(key: String): T {
        if (keys.containsKey(key)) {
            return manager.get(keys[key])
        } else {
            throw RuntimeException("Asset named as \"$key\" not found in the package [ ${this.key} ]")
        }
    }

    override fun dispose() {
        manager.clear()
        keys.clear()
    }
}