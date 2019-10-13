package com.disgraded.gdxmachine.framework.resources

import com.disgraded.gdxmachine.framework.Module

class ResourceModule : Module {


    val packages = hashMapOf<String, AssetPackage>()

    override fun load() {

    }

    override fun unload() {

    }

    fun update(deltaTime: Float) {

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