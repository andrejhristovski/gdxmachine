package com.disgraded.gdxmachine.framework.core.resources

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ResourceApi(private val resourceModule: ResourceModule) {

    fun load(assetPackageClass: KClass<out AssetPackage>, sync: Boolean = false): AssetPackage {
        val assetPackage = assetPackageClass.createInstance()
        resourceModule.loadPackage(assetPackage, sync)
        return assetPackage
    }

    fun get(key: String): AssetPackage = resourceModule.getPackage(key)

    fun unload(key: String) = resourceModule.unloadPackage(key)

    fun unload(assetPackage: AssetPackage) = resourceModule.unloadPackage(assetPackage.key)

    fun exist(key: String): Boolean = resourceModule.existPackage(key)

    fun contains(assetPackage: AssetPackage): Boolean = resourceModule.existPackage(assetPackage.key)

}