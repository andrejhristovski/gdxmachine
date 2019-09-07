package com.disgraded.gdxmachine.core

object Config {

    object Graphics {
        enum class Scale { FIT, FILL }

        var scale = Scale.FILL
        var width: Float = 1280f
        var height: Float = 720f
    }

    val graphics = Graphics
}