package com.tetris.lsw.tetris

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
    }

    private fun initEvent() {
        btn_left.setOnClickListener { tetris.moveLeft() }
        btn_down.setOnClickListener { tetris.fastDown() }
        btn_right.setOnClickListener { tetris.moveRight() }
        btn_rotate.setOnClickListener { tetris.rotate() }
        btn_start.setOnClickListener { tetris.start() }
    }
}
