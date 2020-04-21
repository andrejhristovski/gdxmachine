package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Rectangle
import com.disgraded.gdxmachine.framework.drawables.Sprite

class Rectangle2DComponent: Rectangle(), Drawable2DComponent<Rectangle> {
    override val absolute: Rectangle = Rectangle()
    override var layer: String? = null
}