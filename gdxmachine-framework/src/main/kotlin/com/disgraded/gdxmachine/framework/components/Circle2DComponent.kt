package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Circle

class Circle2DComponent: Circle(), Drawable2DComponent<Circle> {
    override val absolute: Circle = Circle()
    override var layer: String? = null
}