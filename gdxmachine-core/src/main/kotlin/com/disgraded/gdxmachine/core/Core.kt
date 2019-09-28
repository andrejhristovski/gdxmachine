package com.disgraded.gdxmachine.core

import com.disgraded.gdxmachine.core.api.engine.EngineModule
import com.disgraded.gdxmachine.core.api.graphics.GraphicsModule
import com.disgraded.gdxmachine.core.api.input.InputModule
import com.disgraded.gdxmachine.core.api.physics.PhysicsModule
import com.disgraded.gdxmachine.core.api.resource.ResourceModule
import com.disgraded.gdxmachine.core.api.scene.SceneModule

/** Core class is inner class of the engine, Core is used to define lifecycle of the engine modules, and contains
 * interfaces for easy integration of engine modules & APIs This class is singleton and is invoked by the platform */
class Core private constructor(private val entryPoint: EntryPoint) {

    /** Core.Api is interface for Module API  */
    interface Api

    /** Core.Module is interface for engine module. Provides functions and properties used by the lifecycle */
    interface Module {
        val api : Api

        /** Module.load() is invoked at boot stage of the engine before initializing */
        fun load(core: Core, config: Config)

        /** Module.update() is invoked at every update call from the ApplicationListener */
        fun update(deltaTime: Float)

        /** Module.unload() is invoked after executing exit singnal */
        fun unload()
    }

    companion object {

        lateinit var appListener: ApplicationListener
        private lateinit var core: Core

        /** Core.run() is invoked by the platform code and is used for creating singleton instance of the Core class
         * and creating an instance of ApplicationListener class */
        fun run(entryPoint: EntryPoint) {
            core = Core(entryPoint)
            appListener = ApplicationListener(core)
        }
    }

    val context = Context

    private val resourceModule = ResourceModule()
    private val engineModule = EngineModule()
    private val sceneModule = SceneModule()
    private val graphicsModule = GraphicsModule()
    private val inputModule = InputModule()
    private val physicsModule = PhysicsModule()

    /** Core.load() is invoked from ApplicationListener.create() method and is used to boot the engine,
     *  create the context, invoking the load methods of every module by specific order
     *  and invoke EntryPoint.initialize() This method is invoked after OpenGL initialization */
    fun load() {
        context.resources = resourceModule.api as ResourceModule.Api
        context.engine = engineModule.api as EngineModule.Api
        context.scene = sceneModule.api as SceneModule.Api
        context.graphics = graphicsModule.api as GraphicsModule.Api
        context.input = inputModule.api as InputModule.Api
        context.physics = physicsModule.api as PhysicsModule.Api

        var config = entryPoint.configure()
        if (config == null) config = Config

        resourceModule.load(this, config)
        engineModule.load(this, config)
        sceneModule.load(this, config)
        graphicsModule.load(this, config)
        inputModule.load(this, config)
        physicsModule.load(this, config)

        entryPoint.initialize(context)
    }

    /** Core.unload() is invoked from ApplicationListener.dispose() method and is used to unload the modules and
     * dispose the data, also in this method is defined the order of disposing and unloading. */
    fun unload() {
        engineModule.unload()
        sceneModule.destroy()
        entryPoint.destroy()

        sceneModule.unload()
        resourceModule.unload()
        graphicsModule.unload()
        inputModule.unload()
        physicsModule.unload()
    }

    /** Core.update method is invoked from the ApplicationListener and is used for defining update order of the modules */
    fun update(deltaTime: Float) {
        resourceModule.update(deltaTime)
        engineModule.update(deltaTime)
        sceneModule.update(deltaTime)
        physicsModule.update(deltaTime)
        inputModule.update(deltaTime)
        graphicsModule.update(deltaTime)
    }

    /** Core.pause() is invoked by the ApplicationListener for pausing the engine when the application goes to idle state */
    fun pause() {
        sceneModule.pause()
    }

    /** Core.resume() is invoked when the application change the state from idle to running */
    fun resume() {
        sceneModule.resume()
    }

    /** Core.resize() is invoked on application resize */
    fun resize(width: Int, height: Int) {
        graphicsModule.resize(width, height)
    }

    /** Core.reset() is invoked from the engine module when the scene is changed. The reset method is used to reset the
     * state of other modules and dispose the data used in the previous scene */
    fun reset() {
        engineModule.clear()
        graphicsModule.clear()
        inputModule.clear()
        physicsModule.clear()
    }
}