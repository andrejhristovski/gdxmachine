package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.core.engine.Component
import com.disgraded.gdxmachine.framework.core.graphics.Drawable2D

class Render2DComponent: Component {
    private val map = hashMapOf<String, Drawable2D>()
    private val list = arrayListOf<Drawable2D>()

    fun add(key:String, drawable2D: Drawable2D): Drawable2D {
        if (map.containsKey(key)) {
            throw RuntimeException("The key $key already exist in drawable list")
        }
        map[key] = drawable2D
        list.add(drawable2D)
        return drawable2D
    }

    fun remove(key: String) {
        if (!map.containsKey(key)) {
            throw RuntimeException("The key $key doesn't exist")
        }
        val drawable2D: Drawable2D = map[key]!!
        list.remove(drawable2D)
        map.remove(key)
    }

    fun get(key: String): Drawable2D? = map[key]

    fun getAll(): ArrayList<Drawable2D> = list
}