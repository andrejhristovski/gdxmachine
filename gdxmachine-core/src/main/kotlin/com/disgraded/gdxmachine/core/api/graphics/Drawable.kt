package com.disgraded.gdxmachine.core.api.graphics

abstract class Drawable(val type: Type) {

    enum class Type(val key: String) { SPRITE("sprite"), SHAPE("shape") }

    enum class Effect(val key: String) { TINT("tint"), FILL("fill"),
        INVERT("invert"), GREYSCALE("greyscale"), GREYSCALE_COLORED("greyscale_colored"),
        SEPIA("sepia") }

    var x: Float = 0f
    var y: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var pivotX: Float = .5f
    var pivotY: Float = .5f
    var rotation: Float = 0f
    var visible: Boolean = true
    var alpha: Float = 1f
    var effect = Effect.TINT
    var color: Color = Color("#ffffff")
    var intensity: Float = 1f
}