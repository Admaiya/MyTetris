package com.tetris.lsw.tetris.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import cn.leo.tetris.game.Control
import com.tetris.lsw.tetris.bean.*
import java.util.*
import kotlin.concurrent.timerTask

class TetrisView : View, Control {
    val mBoard: Board = Board()
    var mSquare: Square = Square(0)
    var boardHeight: Int = 0
    var boardWidth: Int = 0
    var cellSide: Float = 0f
    var isPause: Boolean = false
    private val mPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL_AND_STROKE
    }

    var timer: Timer? = null

    override fun start() {
        mBoard.clear()
        getNewSquare()
        if (timer != null) {
            timer!!.cancel()
        }
        timer = Timer()
        timer!!.schedule(timerTask {
            this@TetrisView.post {
                fastDown()
                invalidate()
            }
        }, 1000, 1000)
    }

    override fun pause() {
        isPause = !isPause
        if (isPause) {
            if (timer != null) {
                timer!!.cancel()
            }
        } else {
            timer = Timer()
            timer!!.schedule(timerTask {
                this@TetrisView.post {
                    fastDown()
                    invalidate()
                }
            }, 1000, 1000)
        }
    }

    override fun moveLeft() {
        if (isPause) return
        mSquare.moveLeft(mBoard)
        invalidate()
    }

    override fun moveRight() {
        if (isPause) return
        mSquare.moveRight(mBoard)
        invalidate()
    }

    override fun fastDown() {
        if (isPause) return
        if (!mSquare.moveDown(mBoard)) {
            mBoard.squareFall(mSquare)
            getNewSquare()
        }
        invalidate()
    }

    override fun rotate() {
        if (isPause) return
        mSquare.rotate(mBoard)
        invalidate()
    }

    private fun getNewSquare() {
        if (mBoard.getColor(BOARD_WIDTH_SIZE / 2, BOARD_HEIGHT_SIZE - 2) == 1) {
            if (timer != null) {
                timer!!.cancel()
            }
            return
        }
        mSquare = Square(Random().nextInt(6) + 1)
        mSquare.centerY = BOARD_HEIGHT_SIZE - 2
        mSquare.centerX = BOARD_WIDTH_SIZE / 2
        invalidate()
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (width > height / BOARD_RATIO) {
            boardWidth = (height / BOARD_RATIO).toInt()
            boardHeight = height
        } else {
            boardWidth = width
            boardHeight = (BOARD_RATIO * width).toInt()
        }
        cellSide = (boardWidth / BOARD_WIDTH_SIZE).toFloat()
        Log.e("tag", "width:$width  height:$height\nboardWidth:$boardWidth  boardHeight:$boardHeight  cellSide:$cellSide")
    }

    //invalidate()
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.FILL_AND_STROKE
        for (y in 0..(BOARD_HEIGHT_SIZE - 1)) {
            for (x in 0..(BOARD_WIDTH_SIZE - 1)) {
                if (mBoard.getColor(x, y) == 1) {
                    canvas!!.drawRect(x * cellSide, (BOARD_HEIGHT_SIZE - (y + 1)) * cellSide,
                            (x + 1) * cellSide, (BOARD_HEIGHT_SIZE - y) * cellSide, mPaint)
                }
            }
        }
        val pathOnBoard = mSquare.getPathOnBoard()
        for (point in pathOnBoard) {
            val x = point.x
            val y = point.y
            val top = BOARD_HEIGHT_SIZE - (y + 1)
            val bottom = BOARD_HEIGHT_SIZE - y
            canvas!!.drawRect(x * cellSide, top * cellSide,
                    (x + 1) * cellSide, bottom * cellSide, mPaint)
        }

        mPaint.color = Color.RED
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 1f
        canvas!!.drawRect(0f, 0f,
                BOARD_WIDTH_SIZE * cellSide, BOARD_HEIGHT_SIZE * cellSide, mPaint)
    }
}