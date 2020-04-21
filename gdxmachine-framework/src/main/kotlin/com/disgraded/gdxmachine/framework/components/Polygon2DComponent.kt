package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Polygon

class Polygon2DComponent: Polygon(), Drawable2DComponent<Polygon> {
    override val absolute: Polygon = Polygon()
    override var layer: String? = null
}