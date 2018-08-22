package com.tetris.lsw.tetris.bean

class Square {
    var centerX: Int = 0
    var centerY: Int = 0
    var path: ArrayList<SquarePoint> = ArrayList()
    var color: Int = 1

    constructor(type: Int) {
        when (type) {
            1 -> {
                /**
                 * 0100
                 * 0X00
                 * 0100
                 * 0100
                 */
                path.add(SquarePoint(0, 1))
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(0, -1))
                path.add(SquarePoint(0, -2))
            }
            2 -> {
                /**
                 * 0000
                 * 0X10
                 * 0110
                 * 0000
                 */
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(1, 0))
                path.add(SquarePoint(0, -1))
                path.add(SquarePoint(1, -1))
            }
            3 -> {
                /**
                 * 0000
                 * 0X10
                 * 1100
                 * 0000
                 */
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(1, 0))
                path.add(SquarePoint(-1, -1))
                path.add(SquarePoint(0, -1))
            }
            4 -> {
                /**
                 * 0000
                 * 1X00
                 * 0110
                 * 0000
                 */
                path.add(SquarePoint(-1, 0))
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(0, -1))
                path.add(SquarePoint(1, -1))
            }
            5 -> {
                /**
                 * 0100
                 * 0X00
                 * 1100
                 * 0000
                 */
                path.add(SquarePoint(0, 1))
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(-1, -1))
                path.add(SquarePoint(0, -1))
            }
            6 -> {
                /**
                 * 0100
                 * 0X00
                 * 0110
                 * 0000
                 */
                path.add(SquarePoint(0, 1))
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(0, -1))
                path.add(SquarePoint(1, -1))
            }
            7 -> {
                /**
                 * 0100
                 * 1X10
                 * 0000
                 * 0000
                 */
                path.add(SquarePoint(0, 1))
                path.add(SquarePoint(-1, 0))
                path.add(SquarePoint(0, 0))
                path.add(SquarePoint(1, 0))
            }
        }
    }

    fun getPathOnBoard(): ArrayList<SquarePoint> {
        val centerX1 = centerX
        val centerY1 = centerY
        var pathOnBoard: ArrayList<SquarePoint> = ArrayList()
        for (sp in path) {
            val x = centerX1 + sp.x
            val y = centerY1 + sp.y
            pathOnBoard.add(SquarePoint(x, y))
        }
        return pathOnBoard
    }

    fun moveLeft(board: Board): Boolean {
        for (sp in path) {
            val x = centerX + sp.x - 1
            val y = centerY + sp.y
            if (board.isOut(x, y) || board.getColor(x, y) != 0) {
                return false
            }
        }
        centerX--
        return true
    }

    fun moveRight(board: Board): Boolean {
        for (sp in path) {
            val x = centerX + sp.x + 1
            val y = centerY + sp.y
            if (board.isOut(x, y) || board.getColor(x, y) != 0) {
                return false
            }
        }
        centerX++
        return true
    }

    fun moveDown(board: Board): Boolean {
        for (sp in path) {
            val x = centerX + sp.x
            val y = centerY + sp.y - 1
            if (board.isOut(x, y) || board.getColor(x, y) != 0) {
                return false
            }
        }
        centerY--
        return true
    }

    fun rotate(board: Board): Boolean {
        var newPath: ArrayList<SquarePoint> = ArrayList()
        for (sp in path) {
            val newSP = SquarePoint()
            newSP.x = sp.y
            newSP.y = 0 - sp.x
            val x = centerX + newSP.x
            val y = centerY + newSP.y - 1
            if (board.isOut(x, y) || board.getColor(x, y) != 0) {
                return false
            }
            newPath.add(newSP)
        }
        path.clear()
        path.addAll(newPath)
        return true
    }
}

class SquarePoint(var x: Int = 0, var y: Int = 0)