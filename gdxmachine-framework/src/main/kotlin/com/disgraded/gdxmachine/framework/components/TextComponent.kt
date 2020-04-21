package com.disgraded.gdxmachine.framework.components

import com.disgraded.gdxmachine.framework.drawables.Text

class TextComponent: Text(), Drawable2DComponent<Text> {
    override val absolute: Text = Text()
    override var layer: String? = null
}