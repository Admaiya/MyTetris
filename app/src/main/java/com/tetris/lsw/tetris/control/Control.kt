package cn.leo.tetris.game

/**
 * create by : Jarry Leo
 * date : 2018/8/17 10:04
 */
interface Control {
    /**
     * 开始
     */
    fun start()

    /**
     * 暂停
     */
    fun pause()

    /**
     * 左移
     */
    fun moveLeft()

    /**
     * 右移
     */
    fun moveRight()

    /**
     * 快速下坠
     */
    fun fastDown()

    /**
     * 旋转
     */
    fun rotate()
}