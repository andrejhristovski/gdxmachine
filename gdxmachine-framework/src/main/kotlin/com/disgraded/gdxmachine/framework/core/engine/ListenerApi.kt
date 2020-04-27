package com.disgraded.gdxmachine.framework.core.engine

import com.badlogic.ashley.core.Engine

class ListenerApi(private val engine: Engine) {

    private val listenerMap = HashMap<String, Listener>()

    fun add(listener: Listener) {
        if (listenerMap.containsKey(listener.uid)) {
            throw RuntimeException("The listener ${listener.uid} is already added")
        }
        engine.addEntityListener(listener.family, listener.priority, listener)
        listenerMap[listener.uid] = listener
    }

    fun remove(listener: Listener) {
        if (listenerMap.containsKey(listener.uid)) {
            engine.removeEntityListener(listener)
            listenerMap.remove(listener.uid)
        } else {
            throw RuntimeException("Listener ${listener.uid} doesn't exist, can not be removed")
        }
    }

    fun remove(uid: String) {
        if (listenerMap.containsKey(uid)) {
           engine.removeEntityListener(listenerMap[uid])
            listenerMap.remove(uid)
        } else {
            throw RuntimeException("Listener $uid doesn't exist, can not be removed")
        }
    }

    fun get(uid: String): Listener? = listenerMap[uid]

    fun getAll(): ArrayList<Listener> {
        val list = ArrayList<Listener>()
        for (key in listenerMap.keys) {
            list.add(listenerMap[key]!!)
        }
        return list
    }

    fun removeAll() {
        for (key in listenerMap.keys) {
            remove(listenerMap[key]!!)
        }
        listenerMap.clear()
    }



}