package com.disgraded.gdxmachine.framework.graphics.viewport

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport

class Projection {

    val camera = OrthographicCamera()
    private val viewport = ScalingViewport(Scaling.none, 0f, 0f, camera)
}