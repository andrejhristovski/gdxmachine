package com.disgraded.gdxmachine.core.api.resource

import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/** ResourceModule is module for managing game assets. */
class ResourceModule : Core.Module {

    class Api(private val resourceModule: ResourceModule) : Core.Api {

        /** ResourceModule.Api#load() register provided AssetPackage for immediately or asynchronously loading of group of assets  */
        fun load(assetPackageClass: KClass<out AssetPackage>, sync: Boolean = false) {
            val assetPackage = assetPackageClass.createInstance()
            resourceModule.loadPackage(assetPackage.packageKey, assetPackage, sync)
        }

        /** ResourceModule.Api#get() returns an already loaded asset  */
        fun <T> get(packageKey: String, resourceKey: String): T
                = resourceModule.getAsset(packageKey, resourceKey)

        /** ResourceModule.Api#unload unloads group of assets defined by some package key */
        fun unload(packageKey: String) = resourceModule.unloadPackage(packageKey)

        /** ResourceModule.Api#clear clears all loaded assets */
        fun clear() = resourceModule.clear()

        /** ResourceModule.Api#getPendingPackages returns number of pending packages  */
        fun getPendingPackages(): Int {
            return resourceModule.pendingPackages.size
        }

        /** ResourceModule.Api#getCurrentProgress returns loading progress of current asset package */
        fun getCurrentProgress(): Float {
            if (resourceModule.current !== null) {
                return resourceModule.current!!.second.getProgress()
            }
            return 0f
        }

        /** ResourceModule.Api#isLoadingFinished returns true if all asset packages are loaded  */
        fun isLoadingFinished(): Boolean {
            return resourceModule.pendingPackages.size == 0 && resourceModule.current == null
        }
    }

    override val api: Core.Api = Api(this)

    private val pendingPackages = arrayListOf<Pair<String, AssetPackage>>()
    private val pendingPackageKeys = arrayListOf<String>()
    private val loadedPackages = hashMapOf<String, AssetPackage>()
    private var current: Pair<String, AssetPackage>? = null

    override fun load(core: Core, config: Config) {

    }

    /** ResourceModule#update is invoked in main lifecycle of the engine and this method take care of async loading
     * of the asset packages */
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