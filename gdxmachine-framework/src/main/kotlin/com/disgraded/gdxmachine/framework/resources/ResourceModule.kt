package com.disgraded.gdxmachine.framework.resources

import com.disgraded.gdxmachine.framework.Module

class ResourceModule : Module {

    private val packages = hashMapOf<String, AssetPackage>()

    val api = ResourceApi(this)

    override fun load() {}

    override fun unload() {
        for ((key, assetPackage) in packages) {
            assetPackage.dispose()
        }
        packages.clear()
    }

    fun update(deltaTime: Float) {
        for ((key, assetPackage) in packages) {
            if (assetPackage.done) continue
            assetPackage.proceed()
        }
    }

    fun loadPackage(assetPackage: AssetPackage, sync: Boolean) {
        if (packages.containsKey(assetPackage.key)) throw RuntimeException("")
        packages[assetPackage.key] = assetPackage
    }

    fun getPackage(key: String): AssetPackage {
        if (!packages.containsKey(key)) throw RuntimeException("")
        return packages[key]!!
    }

    fun unloadPackage(key: String) {
        if (!packages.containsKey(key)) throw RuntimeException("")
        packages[key]!!.dispose()
        packages.remove(key)
    }

    fun containsPackage(key: String): Boolean {
        return packages.containsKey(key)
    }
}