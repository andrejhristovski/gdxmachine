package com.disgraded.gdxmachine.core.resources

import com.disgraded.gdxmachine.core.engine.ModuleApi
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ResourceApi(private val resourceModule: ResourceModule) : ModuleApi {

    fun load(assetPackage: KClass<out AssetPackage>, instant: Boolean = false) {
        val packageInstance = assetPackage.createInstance()
        resourceModule.loadPackage(packageInstance.key, packageInstance, instant)
    }

    fun <T> get(packageKey: String, resourceKey: String): T {
        return resourceModule.getAsset(packageKey, resourceKey)
    }

    fun unload(packageKey: String) {
        resourceModule.unloadPackage(packageKey)
    }

    fun clear() {
        resourceModule.clear()
    }
}