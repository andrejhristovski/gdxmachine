package com.disgraded.gdxmachine.core.api.scene

import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/** The goal behind Scene Module is to take care of the scenes. Scene Module provides API for managing scenes, and take
 * care for disposing everything from previous scene when the scene is changed */
class SceneModule : Core.Module {

    class Api(private val sceneModule: SceneModule) : Core.Api {

        /** Scene.Api#set() can be used to change the current scene */
        fun set(sceneClass: KClass<out Scene>) = sceneModule.setScene(sceneClass)
    }

    override val api: Core.Api = Api(this)

    private lateinit var core: Core
    private var nextScene: KClass<out Scene>? = null
    private var currentScene : Scene? = null

    override fun load(core: Core, config: Config) {
        this.core = core
    }

    /** SceneModule#update() is invoked by lifecycle update event and this method take care of the lifecycle of the scene.
     * When the scene is changed, class of the next scene is stored and the update method take care of executing all
     * lifecycle events of the current scene before destroying the scene and initializing the next scene */
    override fun update(deltaTime: Float) {
        if (nextScene !== null) {
            if (currentScene !== null) {
                currentScene!!.destroy()
                core.reset()
            }
            currentScene = nextScene!!.createInstance()
            nextScene = null
            currentScene!!.context = core.context
            currentScene!!.initialize()
        }
        if (currentScene !== null) {
            currentScene!!.update(deltaTime)
        }
    }

    /** SceneModule.unload() is involed by the main lifecycle for unloading the scene module */
    override fun unload() {
        currentScene = null
        nextScene = null
    }

    /** SceneModule.setScene() is invoked by the Scene Api for setting the next scene */
    private fun setScene(sceneClass: KClass<out Scene>) {
        if (nextScene == null) {
            nextScene = sceneClass
        }
    }

    fun destroy() {
        if (currentScene !== null) currentScene!!.destroy()
    }

    fun pause() {
        if (currentScene !== null) currentScene!!.pause()
    }

    fun resume() {
        if (currentScene !== null) currentScene!!.resume()
    }
}