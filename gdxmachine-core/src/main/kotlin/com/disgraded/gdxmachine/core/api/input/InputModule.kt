package com.disgraded.gdxmachine.core.api.input

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.disgraded.gdxmachine.core.Config
import com.disgraded.gdxmachine.core.Core

class InputModule : Core.Module {

    class Api(inputModule: InputModule) : Core.Api {

        val onTouchDown = Signal<Int>()
        val onTouchUp = Signal<Int>()
        val onTouchDragged = Signal<Int>()

        val onMouseClickDown = Signal<String>()
        val onMouseClickUp = Signal<String>()
        val onMouseScroll = Signal<Int>()
        val onMouseMove = Signal<Vector2>()

        val onKeyTyped = Signal<String>()
        val onKeyDown = Signal<String>()
        val onKeyUp = Signal<String>()

        fun getAccelerometerX(): Float = Gdx.input.accelerometerX

        fun getAccelerometerY(): Float = Gdx.input.accelerometerY

        fun getAccelerometerZ(): Float = Gdx.input.accelerometerZ

        fun getAzimuth(): Float = Gdx.input.azimuth

        fun getPitch(): Float = Gdx.input.pitch

        fun getRoll(): Float = Gdx.input.roll

        fun getGyroscopeX(): Float = Gdx.input.gyroscopeX

        fun getGyroscopeY(): Float = Gdx.input.gyroscopeY

        fun getGyroscopeZ(): Float = Gdx.input.gyroscopeZ

        fun getMaxPointers(): Int = Gdx.input.maxPointers

        fun getPosition(pointer: Int = -1): Vector2 {
            return if (pointer == -1) Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            else Vector2(Gdx.input.getX(pointer).toFloat(), Gdx.input.getY(pointer).toFloat())
        }

        fun getDelta(pointer: Int = -1): Vector2 {
            return if (pointer == -1) Vector2(Gdx.input.deltaX.toFloat(), Gdx.input.deltaY.toFloat())
            else Vector2(Gdx.input.getDeltaX(pointer).toFloat(), Gdx.input.getDeltaY(pointer).toFloat())
        }

        fun isTouched(pointer: Int = -1): Boolean = if (pointer == -1) Gdx.input.isTouched else Gdx.input.isTouched(pointer)

        fun justTouched(): Boolean = Gdx.input.justTouched()

        fun getPressure(pointer: Int = -1): Float = if (pointer == -1) Gdx.input.pressure else Gdx.input.getPressure(pointer)

        fun getEventTime(): Long = Gdx.input.currentEventTime

        fun onScreenKeyboard(visible: Boolean) = Gdx.input.setOnscreenKeyboardVisible(visible)

        fun vibrate(ms: Int) = Gdx.input.vibrate(ms)

        fun vibrate(pattern: LongArray, repeat: Int = 1) = Gdx.input.vibrate(pattern, repeat)

        fun cancelVibration() = Gdx.input.cancelVibrate()

        fun setCatchKey(keycode: Int, catchKey: Boolean) = Gdx.input.setCatchKey(keycode, catchKey)

        fun isCatchKey(keycode: Int) = Gdx.input.isCatchKey(keycode)

        fun setCursorVisible(visible: Boolean) {
            Gdx.input.isCursorCatched = visible
        }

        fun isCursorVisible(): Boolean = Gdx.input.isCursorCatched

        fun setCursorPosition(x: Int, y: Int) = Gdx.input.setCursorPosition(x, y)
    }

    override val api: Core.Api = Api(this)

    private lateinit var inputProcessor : InputProcessor

    override fun load(core: Core, config: Config) {
        inputProcessor  = InputProcessor(api as Api)
        Gdx.input.inputProcessor = inputProcessor
    }

    override fun update(deltaTime: Float) {

    }

    override fun unload() = clear()

    fun clear() {
        val api = api as Api
        api.onTouchDown.removeAllListeners()
        api.onTouchUp.removeAllListeners()
        api.onTouchDragged.removeAllListeners()
        api.onMouseClickDown.removeAllListeners()
        api.onMouseClickUp.removeAllListeners()
        api.onMouseMove.removeAllListeners()
        api.onMouseScroll.removeAllListeners()
        api.onKeyDown.removeAllListeners()
        api.onKeyUp.removeAllListeners()
        api.onKeyTyped.removeAllListeners()
    }
}