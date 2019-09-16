package com.disgraded.gdxmachine.core.api.graphics

import com.badlogic.gdx.math.Matrix4
import com.disgraded.gdxmachine.core.api.graphics.drawable.Drawable
import com.disgraded.gdxmachine.core.api.graphics.drawable.Light
import com.disgraded.gdxmachine.core.api.graphics.drawable.LightType
import com.disgraded.gdxmachine.core.api.graphics.drawable.Sprite
import com.disgraded.gdxmachine.core.api.graphics.renderer.LightPointAttenuationRenderer
import com.disgraded.gdxmachine.core.api.graphics.renderer.Renderer

class AttenuationBatch(private val projection: Projection) : DrawableBatch {

    private val rendererMap = hashMapOf<String, Renderer>()
    private var currentRenderer: Renderer? = null
    private var currentRendererType: String? = null
    private var gpuCalls = 0

    init {
        rendererMap["light_point_attenuation"] = LightPointAttenuationRenderer(projection)
    }

    override fun render(drawableList: ArrayList<Drawable>, projectionMatrix: Matrix4): Int {
        gpuCalls = 0
        for (renderer in rendererMap.toList()) {
            renderer.second.setProjectionMatrix(projectionMatrix)
        }
        for (drawable in drawableList) {
            adaptCurrentRenderer(drawable)
            currentRenderer!!.draw(drawable)
        }
        if (currentRenderer != null && currentRenderer!!.active) {
            gpuCalls += currentRenderer!!.finish()
        }
        return gpuCalls
    }

    override fun dispose() {
        for(renderer in rendererMap) {
            renderer.value.dispose()
        }
        rendererMap.clear()
    }

    private fun adaptCurrentRenderer(drawable: Drawable) {
        val type = getRendererType(drawable) ?: throw RuntimeException("Wrong drawable type sent to the standard batch [${drawable.type}]")
        if (currentRendererType != type) {
            if (currentRenderer != null && currentRenderer!!.active) {
                gpuCalls += currentRenderer!!.finish()
            }
            currentRendererType = type
            val newRenderer = rendererMap[type] ?: throw RuntimeException("Renderer for type $type isn't registered yet")
            currentRenderer = newRenderer
            currentRenderer!!.start()
        }
        if (!currentRenderer!!.active) {
            currentRenderer!!.start()
        }
    }

    private fun getRendererType(drawable: Drawable): String? {
        return when(drawable.type) {
            Drawable.Type.LIGHT -> when((drawable as Light).lightType) {
                LightType.POINT -> "light_point_attenuation"
            }
            else -> null
        }
    }
}