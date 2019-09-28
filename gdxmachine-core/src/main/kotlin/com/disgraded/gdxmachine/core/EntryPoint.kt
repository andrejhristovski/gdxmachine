package com.disgraded.gdxmachine.core

/** Interface for entry point class
 * The class created using this interface needs to be passed as EntryPoint on any platform.
 * Will be called in the boot stage of the engine & will be used to configure the structure of scenes,
 * set global configuration properties & load assets for first scene
 */
interface EntryPoint {

    /** EntryPoint#configure() is called first, in this method you can configure global settings for the engine as
     *  virtual size of main viewport, type of scaling of the main viewport, etc.
     *  Returning null will set the default global settings */
    fun configure() : Config?

    /** EntryPoint.initialize() is called after boot phase of the engine and can be used for some interactions with
     * engine context (loading asset packages, invoking scene, configuring platform specific features etc.) */
    fun initialize(context: Context)

    /** EntryPoint.destroy() is invoked on destroying engine context, can be invoked from built-in function for
     * terminating or by exit signal from the OS */
    fun destroy()
}