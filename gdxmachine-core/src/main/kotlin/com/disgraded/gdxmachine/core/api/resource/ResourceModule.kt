package com.disgraded.gdxmachine.core.api.resource

import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ResourceModule : Core.Module {

    class ResourceApi(private val resourceModule: ResourceModule) : Core.Api {

        fun load(assetPackageClass: KClass<out AssetPackage>, sync: Boolean = false) {
            val assetPackage = assetPackageClass.createInstance()
            resourceModule.loadPackage(assetPackage.packageKey, assetPackage, sync)
        }

        fun <T> get(packageKey: String, resourceKey: String): T
                = resourceModule.getAsset(packageKey, resourceKey)

        fun unload(packageKey: String) = resourceModule.unloadPackage(packageKey)

        fun clear() = resourceModule.clear()
    }

    override val api: Core.Api = ResourceApi(this)

    private val pendingPackages = arrayListOf<Pair<String, AssetPackage>>()
    private val pendingPackageKeys = arrayListOf<String>()
    private val loadedPackages = hashMapOf<String, AssetPackage>()
    private var current: Pair<String, AssetPackage>? = null

    override fun load(core: Core, config: Config) {

    }

    override fun update(deltaTime: Float) {
        if (current != null) {
            if (current!!.second.update()) {
                loadedPackages[current!!.first] = current!!.second
                current = null
            }
        } else if (pendingPackages.isNotEmpty()) {
            current = pendingPackages.first()
            pendingPackages.remove(pendingPackages.first())
            pendingPackageKeys.remove(pendingPackageKeys.first())
        }
    }

    override fun unload() {
        clear()
    }

    private fun loadPackage(packageKey: String, assetPackage: AssetPackage, sync: Boolean) {
        if (loadedPackages.containsKey(packageKey) || pendingPackageKeys.contains(packageKey)) {
            throw Error("Package with the name \"$packageKey\" is already loaded")
        }
        if (sync) {
            assetPackage.sync()
            loadedPackages[packageKey] = assetPackage
        } else {
            pendingPackageKeys.add(packageKey)
            pendingPackages.add(Pair(packageKey, assetPackage))
        }
    }

    private fun <T> getAsset(packageKey: String, resourceKey: String): T {
        if (loadedPackages.containsKey(packageKey) && loadedPackages[packageKey] != null ) {
            return loadedPackages[packageKey]!!.get(resourceKey)
        } else {
            throw RuntimeException("Package with the name \"$packageKey\" doesn't exist!")
        }
    }

    private fun unloadPackage(packageKey: String) {
        if (loadedPackages.containsKey(packageKey)) {
            loadedPackages[packageKey]!!.dispose()
            loadedPackages.remove(packageKey)
        } else {
            throw RuntimeException("Package with the name \"$packageKey\" doesn't exist!")
        }
    }

    private fun clear() {
        pendingPackages.clear()
        if (current !== null) {
            current!!.second.dispose()
            current = null
        }
        loadedPackages.forEach { it.value.dispose() }
        loadedPackages.clear()
    }

}