package com.disgraded.gdxmachine.framework.graphics.viewport

class Viewport(val key: String) {

    val projection = Projection()
    var gpuCalls = 0
    var lightsEnabled = false

}