package com.disgraded.gdxmachine.framework.core.resources

import com.disgraded.gdxmachine.framework.core.Module

class ResourceModule : Module {

    private val packages = hashMapOf<String, AssetPackage>()

    val api = ResourceApi(this)

    override fun load() {}

    override fun unload() {
        for ((_, assetPackage) in packages) {
            assetPackage.dispose()
        }
        packages.clear()
    }

    fun update() {
        for ((_, assetPackage) in packages) {
            if (assetPackage.done) assetPackage.sync(true)
            assetPackage.proceed()
        }
    }

    fun loadPackage(assetPackage: AssetPackage, sync: Boolean) {
        if (packages.containsKey(assetPackage.key)) throw RuntimeException("")  // TODO: MESSAGE HERE
        packages[assetPackage.key] = assetPackage
        if (sync) assetPackage.sync()
    }

    fun getPackage(key: String): AssetPackage {
        if (!packages.containsKey(key)) throw RuntimeException("")  // TODO: MESSAGE HERE
        return packages[key]!!
    }

    fun unloadPackage(key: String) {
        if (!packages.containsKey(key)) throw RuntimeException("")  // TODO: MESSAGE HERE
        packages[key]!!.dispose()
        packages.remove(key)
    }

    fun existPackage(key: String): Boolean {
        return packages.containsKey(key)
    }
}