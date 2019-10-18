package com.disgraded.gdxmachine.sandbox.source

import com.disgraded.gdxmachine.framework.graphics.Drawable

class Sprite: Drawable {

    override fun getOrder(): Long {
        return 0
    }

    var name = "example"

}