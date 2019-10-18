package com.disgraded.gdxmachine.sandbox.source

import com.badlogic.gdx.utils.Pool

class ExamplePool : Pool<Sprite>() {

    override fun newObject(): Sprite {
        return Sprite()
    }
}