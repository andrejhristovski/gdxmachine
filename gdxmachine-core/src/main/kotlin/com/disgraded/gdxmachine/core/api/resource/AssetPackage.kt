package com.disgraded.gdxmachine.core.api.resource

import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.assets.AssetManager as GdxAssetManager
import com.badlogic.gdx.utils.Disposable
import com.disgraded.gdxmachine.core.api.resource.asset.PlainText
import com.disgraded.gdxmachine.core.api.resource.asset.PlainTextLoader
import com.disgraded.gdxmachine.core.api.resource.asset.PlainTextLoaderParams

open class AssetPackage(val packageKey: String) : Disposable {

    private val gdxAssetManager = GdxAssetManager(InternalFileHandleResolver(), true)
    private val assetKeys = hashMapOf<String, String>()

    init {
        gdxAssetManager.setLoader(FreeTypeFontGenerator::class.java,
                FreeTypeFontGeneratorLoader(gdxAssetManager.fileHandleResolver))
        gdxAssetManager.setLoader(BitmapFont::class.java, ".ttf",
                FreetypeFontLoader(gdxAssetManager.fileHandleResolver))
        gdxAssetManager.setLoader(PlainText::class.java,
                PlainTextLoader(gdxAssetManager.fileHandleResolver))
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

    protected fun loadFont(key: String, path: String, fontParams: FontParams = FontParams(), genMipMaps: Boolean = false,
                           minFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest,
                           magFilter: Texture.TextureFilter = Texture.TextureFilter.Nearest) {
        val fontParameters = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = fontParams.size
            mono = fontParams.mono
            color = Color(fontParams.color.r, fontParams.color.g, fontParams.color.b, fontParams.color.a)
            gamma = fontParams.gamma
            borderWidth = fontParams.borderSize
            borderStraight = fontParams.borderStraight
            borderColor = Color(fontParams.borderColor.r, fontParams.borderColor.g, fontParams.borderColor.b,
                    fontParams.borderColor.a)
            borderGamma = fontParams.borderGamma
            shadowOffsetX = fontParams.shadowOffsetX
            shadowOffsetY = fontParams.shadowOffsetY
            shadowColor = Color(fontParams.shadowColor.r, fontParams.shadowColor.g, fontParams.shadowColor.b,
                    fontParams.shadowColor.a)
            spaceX = fontParams.spaceX
            spaceY = fontParams.spaceY
            padTop = fontParams.padTop
            padLeft = fontParams.padLeft
            padBottom = fontParams.padBottom
            padRight = fontParams.padRight
            kerning = fontParams.kerning
            flip = fontParams.flip
            this.genMipMaps = genMipMaps
            this.minFilter = minFilter
            this.magFilter = magFilter
        }
        val params = FreetypeFontLoader.FreeTypeFontLoaderParameter()
        params.fontFileName = path
        params.fontParameters = fontParameters
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

    protected fun loadText(key: String, path: String) {
        val params = PlainTextLoaderParams(path)
        gdxAssetManager.load(path, PlainText::class.java, params)
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