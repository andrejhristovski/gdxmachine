package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Mask

class MaskComponent: Mask(), Drawable2DComponent<Mask> {
    override val absolute: Mask = Mask()
    override var layer: String? = null
}