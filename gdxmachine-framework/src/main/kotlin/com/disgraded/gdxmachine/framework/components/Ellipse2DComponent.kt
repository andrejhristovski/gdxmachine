package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Ellipse

class Ellipse2DComponent: Ellipse(), Drawable2DComponent<Ellipse> {
    override val absolute: Ellipse = Ellipse()
    override var layer: String? = null
}