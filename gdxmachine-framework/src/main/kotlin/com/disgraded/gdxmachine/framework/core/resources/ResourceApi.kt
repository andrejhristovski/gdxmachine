package com.disgraded.gdxmachine.framework.core.resources

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ResourceApi(private val resourceModule: ResourceModule) {

    fun load(assetPackage: AssetPackage, sync: Boolean = false): AssetPackage {
        resourceModule.loadPackage(assetPackage, sync)
        return assetPackage
    }

    fun getPackage(key: String): AssetPackage = resourceModule.getPackage(key)

    fun unload(key: String) = resourceModule.unloadPackage(key)

    fun unload(assetPackage: AssetPackage) = resourceModule.unloadPackage(assetPackage.key)

    fun exist(key: String): Boolean = resourceModule.existPackage(key)

    fun contains(assetPackage: AssetPackage): Boolean = resourceModule.existPackage(assetPackage.key)

}