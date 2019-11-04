package com.disgraded.gdxmachine.framework.core.input

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input as GdxInput
import com.badlogic.gdx.InputProcessor as GdxInputProcessor

class InputProcessor(private val api: InputApi) : GdxInputProcessor {

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
        api.onMouseMove.dispatch(null)
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
        api.onKeyUp.dispatch(GdxInput.Keys.toString(keycode))
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        api.onKeyDown.dispatch(GdxInput.Keys.toString(keycode))
        return true
    }

    private fun getMouseButton(button: Int): String {
        return when(button) {
            GdxInput.Buttons.LEFT -> "LEFT"
            GdxInput.Buttons.RIGHT -> "RIGHT"
            GdxInput.Buttons.MIDDLE -> "MIDDLE"
            GdxInput.Buttons.BACK -> "BACK"
            GdxInput.Buttons.FORWARD -> "FORWARD"
            else -> "BUTTON-$button"
        }
    }
}