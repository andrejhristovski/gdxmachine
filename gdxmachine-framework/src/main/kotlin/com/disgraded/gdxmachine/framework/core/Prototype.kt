package com.disgraded.gdxmachine.framework.core

interface Prototype<T> {
    fun copy(): T

    fun inherit(obj: T)
}