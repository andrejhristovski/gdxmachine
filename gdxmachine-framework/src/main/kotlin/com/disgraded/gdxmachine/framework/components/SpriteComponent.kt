package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Sprite

class SpriteComponent: Sprite(), Drawable2DComponent<Sprite> {
    override val absolute: Sprite = Sprite()
    override var layer: String? = null
}