package com.disgraded.gdxmachine.framework.resources

class ResourceApi(private val resourceModule: ResourceModule) {

    fun load(assetPackage: AssetPackage, sync: Boolean = false) = resourceModule.loadPackage(assetPackage, sync)

    fun get(key: String): AssetPackage = resourceModule.getPackage(key)

    fun unload(key: String) = resourceModule.unloadPackage(key)

    fun unload(assetPackage: AssetPackage) = resourceModule.unloadPackage(assetPackage.key)

    fun contains(key: String): Boolean = resourceModule.containsPackage(key)

    fun contains(assetPackage: AssetPackage): Boolean = resourceModule.containsPackage(assetPackage.key)

}