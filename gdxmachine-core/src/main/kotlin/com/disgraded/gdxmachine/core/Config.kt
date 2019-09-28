package com.disgraded.gdxmachine.core

/** Config object is used to configure global settings at boot stage of the engine */
object Config {

    enum class Scale { FIT, FILL }

    var scale = Scale.FIT
    var width: Float = 1280f
    var height: Float = 720f
}