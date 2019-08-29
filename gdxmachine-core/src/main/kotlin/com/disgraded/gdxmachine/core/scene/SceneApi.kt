package com.disgraded.gdxmachine.core.scene

import com.disgraded.gdxmachine.core.engine.module.ModuleApi
import kotlin.reflect.KClass

class SceneApi(private val sceneModule: SceneModule) : ModuleApi {

    fun set(sceneClass: KClass<out Scene>) {
        sceneModule.setScene(sceneClass)
    }

}