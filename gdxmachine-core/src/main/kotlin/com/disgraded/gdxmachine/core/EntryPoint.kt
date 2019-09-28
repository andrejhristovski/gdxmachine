package com.disgraded.gdxmachine.core

/** Interface for entry point class
 * The class created using this interface needs to be passed as a entry point in the platform.
 * The methods of the passed class will be called by the main lifecycle in the boot stage of the engine.
 * Will be used to set the first scene, set global configuration properties, load assets etc.
 */
interface EntryPoint {

    /** EntryPoint#configure() is called first, in this method you can configure global settings for the engine as
     *  virtual size of main viewport, type of scaling of the main viewport, etc.
     *  Returning null will set the default values of global settings */
    fun configure() : Config?

    /** EntryPoint#initialize() is called after boot phase of the engine and can be used for some interactions with
     * engine context (loading asset packages, invoking scene, configuring platform specific features etc.)
     * @param context Context
     * */
    fun initialize(context: Context)

    /** EntryPoint#destroy() is invoked on destroying lifecycle event of the engine.
     * Can be invoked from built-in function for terminating the application or by exit signal from the OS */
    fun destroy()
}