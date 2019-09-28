package com.disgraded.gdxmachine.core.api.input

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.InputProcessor as GdxInputProcessor

class InputProcessor(private val api: InputModule.Api) : GdxInputProcessor {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        when(Gdx.app.type) {
            Application.ApplicationType.Android -> api.onTouchUp.dispatch(pointer)
            Application.ApplicationType.Desktop -> api.onMouseClickUp.dispatch(getMouseButton(button))
            else -> throw RuntimeException("Unhandled platform")
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        when(Gdx.app.type) {
            Application.ApplicationType.Android -> api.onTouchDown.dispatch(pointer)
            Application.ApplicationType.Desktop -> api.onMouseClickDown.dispatch(getMouseButton(button))
            else -> throw RuntimeException("Unhandled platform")
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        api.onTouchDragged.dispatch(pointer)
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        api.onMouseMove.dispatch(Vector2(screenX.toFloat(), screenY.toFloat()))
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        api.onKeyTyped.dispatch(character.toString())
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        api.onMouseScroll.dispatch(amount)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        api.onKeyUp.dispatch(Input.Keys.toString(keycode))
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        api.onKeyDown.dispatch(Input.Keys.toString(keycode))
        return true
    }

    private fun getMouseButton(button: Int): String {
        return when(button) {
            Input.Buttons.LEFT -> "LEFT"
            Input.Buttons.RIGHT -> "RIGHT"
            Input.Buttons.MIDDLE -> "MIDDLE"
            Input.Buttons.BACK -> "BACK"
            Input.Buttons.FORWARD -> "FORWARD"
            else -> "BUTTON-$button"
        }
    }
}