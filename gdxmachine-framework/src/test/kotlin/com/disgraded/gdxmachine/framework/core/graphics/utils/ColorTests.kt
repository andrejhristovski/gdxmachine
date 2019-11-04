package com.disgraded.gdxmachine.framework.core.graphics.utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import java.lang.RuntimeException

class ColorTests {

    @Test
    fun `initialisation with a correct hex value and alpha works`() {
        val hex = Color("#1a2BC6", 0.6f)
        assertEquals(0.101960786f, hex.r)
        assertEquals(0.16862746f, hex.g)
        assertEquals(0.7764706f, hex.b)
        assertEquals(0.6f, hex.a)
    }

    @Test
    fun `initialisation with an incorrect hex value and correct alpha throws an exception`() {
        assertThrows(Exception::class.java) {
            Color("#001ghK", 1.0f)
        }
    }

    @Test
    fun `initialisation with correct float values works`() {
        val color = Color(0.1f, 0.17f, 0.78f, 0.6f)
        assertEquals(0.1f, color.r)
        assertEquals(0.17f, color.g)
        assertEquals(0.78f, color.b)
        assertEquals(0.6f, color.a)
    }

    @Test
    fun `initialisation with incorrect float values throws an exception`() {
        assertThrows(RuntimeException::class.java) {
            Color(1.2f, 0.2f, 0.5f, -1.2f)
        }
    }
}
