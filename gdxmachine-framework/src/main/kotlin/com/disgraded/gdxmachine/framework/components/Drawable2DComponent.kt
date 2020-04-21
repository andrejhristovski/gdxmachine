package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.core.engine.Component
import com.disgraded.gdxmachine.framework.drawables.Drawable2D

interface Drawable2DComponent<T: Drawable2D>: Component {
    val absolute: T
    var layer: String?
}