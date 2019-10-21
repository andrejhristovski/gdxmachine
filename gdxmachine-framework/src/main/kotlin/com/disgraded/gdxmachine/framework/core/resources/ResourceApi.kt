package com.disgraded.gdxmachine.framework.core.resources

class ResourceApi(private val resourceModule: ResourceModule) {

    fun load(assetPackage: AssetPackage, sync: Boolean = false) = resourceModule.loadPackage(assetPackage, sync)

    fun get(key: String): AssetPackage = resourceModule.getPackage(key)

    fun unload(key: String) = resourceModule.unloadPackage(key)

    fun unload(assetPackage: AssetPackage) = resourceModule.unloadPackage(assetPackage.key)

    fun exist(key: String): Boolean = resourceModule.existPackage(key)

    fun contains(assetPackage: AssetPackage): Boolean = resourceModule.existPackage(assetPackage.key)

}