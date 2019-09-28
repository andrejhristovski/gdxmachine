package com.disgraded.gdxmachine.core.api.scene

import com.disgraded.gdxmachine.core.Context

/** Scene abstract class is used to be inherited by the implementation of specific scene.
 * The Scene class contains the engine context and lifecycle methods */
abstract class Scene {

    lateinit var context: Context

    /** Scene#initialize() is the first invoked method in the scene life cycle. This method is invoked
     * only on the beginning and is used to setup the scene, create the viewports, physics worlds, initialize the
     * entities, etc.*/
    abstract fun initialize()

    /** Scene#update() is invoked on every frame and can be used for implementing the game logic */
    abstract fun update(deltaTime: Float)

    /** Scene#pause() is invoked when the application goes into idle state */
    abstract fun pause()

    /** Scene#resume() is invoked when the application change the state from iddle to running */
    abstract fun resume()

    /** Scene#destroy() is invoked before changing from one scene to another and is invoked on the exit signal of the
     * application */
    abstract fun destroy()

}