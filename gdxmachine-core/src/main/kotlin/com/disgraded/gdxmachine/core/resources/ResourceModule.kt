package com.disgraded.gdxmachine.core.resources

import com.disgraded.gdxmachine.core.engine.Game
import com.disgraded.gdxmachine.core.engine.Module
import com.disgraded.gdxmachine.core.engine.ModuleApi

class ResourceModule(private val game: Game) : Module {

    private val assetsApi = ResourceApi(this)

    override val api: ModuleApi
        get() = assetsApi

    private val pending: ArrayList<Pair<String, AssetPackage>> = arrayListOf()
    private val loaded: HashMap<String, AssetPackage?> = hashMapOf()
    private var current: Pair<String, AssetPackage>? = null


    override fun load() {

    }

    fun update() {
        if (current != null) {
            if (current!!.second.update()) {
                loaded[current!!.first] = current!!.second
                current = null
            }
        } else if (pending.isNotEmpty()) {
            current = pending.first()
            pending.remove(pending.first())
        }
    }

    override fun unload() {
        clear()
    }

    fun loadPackage(packageKey: String, assetPackage: AssetPackage, instant: Boolean) {
        if (loaded.containsKey(packageKey)) {
            throw Error("Package with the name \"$packageKey\" is already loaded")
        }
        if (instant) {
            assetPackage.instant()
            loaded[packageKey] = assetPackage
        } else {
            loaded[packageKey] = null
            pending.add(Pair(packageKey, assetPackage))
        }
    }

    fun <T> getAsset(packageKey: String, resourceKey: String): T {
        if (loaded.containsKey(packageKey) && loaded[packageKey] != null ) {
            return loaded[packageKey]!!.get(resourceKey)
        } else {
            throw RuntimeException("Package with the name \"$packageKey\" doesn't exist!")
        }
    }

    fun unloadPackage(packageKey: String) {
        if (loaded.containsKey(packageKey)) {
            loaded[packageKey]!!.dispose()
            loaded.remove(packageKey)
        } else {
            throw RuntimeException("Package with the name \"$packageKey\" doesn't exist!")
        }
    }

    fun clear() {
        pending.clear()
        if (current !== null) {
            current!!.second.dispose()
            current = null
        }
        loaded.forEach {
            if (it.value !== null) {
                it.value!!.dispose()
            }
        }
        loaded.clear()
    }
}