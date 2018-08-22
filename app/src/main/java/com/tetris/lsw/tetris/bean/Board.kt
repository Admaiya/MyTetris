package com.tetris.lsw.tetris.bean

val BOARD_HEIGHT_SIZE = 20
val BOARD_WIDTH_SIZE = 10
val BOARD_RATIO:Float = BOARD_HEIGHT_SIZE.toFloat()/BOARD_WIDTH_SIZE

class Board {
    var map: Array<Array<Cell>> = Array(BOARD_HEIGHT_SIZE) { y -> Array(BOARD_WIDTH_SIZE) { x -> Cell(x, y) } }

    fun changePoint(x: Int, y: Int, color: Int) {
        map[y][x].color = color
    }

    fun clear(){
        map = Array(BOARD_HEIGHT_SIZE) { y -> Array(BOARD_WIDTH_SIZE) { x -> Cell(x, y) } }
    }

    fun squareFall(square: Square) {
        for (sp in square.path) {
            val x = square.centerX + sp.x
            val y = square.centerY + sp.y
            if (map[y][x].color != 0) {
                //有问题
            }
            map[y][x].color = square.color
        }
        clearAllFullLine()
    }

    fun getColor(x: Int, y: Int): Int {
        return map[y][x].color
    }

    fun isOut(x: Int, y: Int): Boolean {
        if (x >= BOARD_WIDTH_SIZE || x < 0 ||
                y >= BOARD_HEIGHT_SIZE || y < 0) {
            return true
        }
        return false
    }

    fun clearAllFullLine() {
        for (lineNum in 0..(BOARD_HEIGHT_SIZE - 1)) {
            if (isFullLine(map[lineNum])) {
                clearLine(lineNum)
                if (isFullLine(map[lineNum])) {
                    clearLine(lineNum)
                    if (isFullLine(map[lineNum])) {
                        clearLine(lineNum)
                        if (isFullLine(map[lineNum])) {
                            clearLine(lineNum)
                        }
                    }
                }
            }
        }
    }

    private fun isFullLine(line: Array<Cell>): Boolean {
        for (cell in line) {
            if (cell.color == 0) {
                return false
            }
        }
        return true
    }

    private fun clearLine(lineNum: Int) {
        for (num in lineNum..(BOARD_HEIGHT_SIZE - 2)) {
            map[num] = map[num + 1]
        }
        map[BOARD_HEIGHT_SIZE - 1] = Array(BOARD_WIDTH_SIZE) { x -> Cell(x, BOARD_HEIGHT_SIZE - 1) }
    }
}