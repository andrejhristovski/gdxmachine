package com.disgraded.gdxmachine.framework.core.graphics.utils

import com.badlogic.gdx.math.MathUtils

class Mesh2D {

    var x1 = 0f
    var y1 = 0f
    var x2 = 0f
    var y2 = 0f
    var x3 = 0f
    var y3 = 0f
    var x4 = 0f
    var y4 = 0f

    private var sizeX = 0f
    private var sizeY = 0f

    private var sin = 0f
    private var cos = 0f
    private var rx1 = 0f
    private var ry1 = 0f
    private var rx2 = 0f
    private var ry2 = 0f
    private var rx3 = 0f
    private var ry3 = 0f

    fun set(transform2D: Transform2D, width: Int, height: Int) {
        sizeX = width * transform2D.scaleX
        sizeY = height * transform2D.scaleY

        x1 = 0 - (sizeX * transform2D.anchorX)
        y1 = 0 - (sizeY * transform2D.anchorY)
        x2 = x1
        y2 = y1 + sizeY
        x3 = x1 + sizeX
        y3 = y1 + sizeY
        x4 = x1 + sizeX
        y4 = y1

        if (transform2D.angle != 0f) {
            sin = MathUtils.sinDeg(transform2D.angle)
            cos = MathUtils.cosDeg(transform2D.angle)
            rx1 = cos * x1 - sin * y1
            ry1 = sin * x1 + cos * y1
            rx2 = cos * x2 - sin * y2
            ry2 = sin * x2 + cos * y2
            rx3 = cos * x3 - sin * y3
            ry3 = sin * x3 + cos * y3
            x1 = rx1
            y1 = ry1
            x2 = rx2
            y2 = ry2
            x3 = rx3
            y3 = ry3
            x4 = x1 + (x3 - x2)
            y4 = y3 - (y2 - y1)
        }

        x1 += transform2D.x
        x2 += transform2D.x
        x3 += transform2D.x
        x4 += transform2D.x
        y1 += transform2D.y
        y2 += transform2D.y
        y3 += transform2D.y
        y4 += transform2D.y
    }

}