package com.disgraded.gdxmachine.framework.graphics.viewport

import com.badlogic.gdx.graphics.OrthographicCamera
import com.disgraded.gdxmachine.framework.graphics.utils.Color

class ViewportApi(private val viewport: Viewport) {

    var ambientColor = Color.BLUE_GREY

    val camera: OrthographicCamera = viewport.projection.camera

    var visible = true

    var z = 0

    fun enableLights(enable: Boolean) {
        viewport.lightsEnabled = enable
    }

    fun isLightsEnabled(): Boolean = viewport.lightsEnabled

    fun getGPUCalls(): Int = viewport.gpuCalls
}