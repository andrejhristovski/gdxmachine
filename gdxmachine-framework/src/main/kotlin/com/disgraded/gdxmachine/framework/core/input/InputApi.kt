package com.disgraded.gdxmachine.framework.core.input

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.utils.Array

class InputApi {

    val onTouchDown = Signal<Int>()
    val onTouchUp = Signal<Int>()
    val onTouchDragged = Signal<Int>()

    val onMouseClickDown = Signal<String>()
    val onMouseClickUp = Signal<String>()
    val onMouseScroll = Signal<Int>()
    val onMouseMove = Signal<Any>()

    val onKeyTyped = Signal<String>()
    val onKeyDown = Signal<String>()
    val onKeyUp = Signal<String>()

    val controllers: Array<Controller>?
        get() = Controllers.getControllers()

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

    fun getX(pointer: Int = -1): Int {
        return if (pointer == -1) Gdx.input.x
        else Gdx.input.getX(pointer)
    }

    fun getY(pointer: Int = -1): Int {
        return if (pointer == -1) Gdx.input.y
        else Gdx.input.getY(pointer)
    }

    fun getDeltaX(pointer: Int = -1): Int {
        return if (pointer == -1) Gdx.input.deltaX
        else Gdx.input.getDeltaX(pointer)
    }

    fun getDeltaY(pointer: Int = -1): Int {
        return if (pointer == -1) Gdx.input.deltaY
        else Gdx.input.getDeltaY(pointer)
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

    fun clear() {
        onTouchDown.removeAllListeners()
        onTouchUp.removeAllListeners()
        onTouchDragged.removeAllListeners()
        onMouseClickDown.removeAllListeners()
        onMouseClickUp.removeAllListeners()
        onMouseScroll.removeAllListeners()
        onMouseMove.removeAllListeners()
        onKeyTyped.removeAllListeners()
        onKeyDown.removeAllListeners()
        onKeyUp.removeAllListeners()
    }
}