package com.revenco.eyepetizer_jetpack.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * 解决首页viewpager2 嵌套 横向RecyclerView，引发的滑动冲突问题
 * 问题原因：在进行横向滑动时候，父类viewpager2，将事件进行了拦截，导致RecyclerView
 * 不能接收到横向滑动事件
 * 解决办法：告诉父容器不进行拦截即可
 */
class HorizontalRecyclerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : RecyclerView(context, attributeSet, def) {

    private var downX = 0f
    private var downY = 0f


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                parent.requestDisallowInterceptTouchEvent(true)

            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x
                val moveY = ev.y
                if (abs(moveX - downX) > abs(moveY - downY)) {
                    parent.requestDisallowInterceptTouchEvent(canScrollHorizontally((downX - moveX).toInt()))
                } else {
                    parent.requestDisallowInterceptTouchEvent(canScrollVertically((downY - moveY).toInt()))
                }
            }
            MotionEvent.ACTION_UP
            -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }

            MotionEvent.ACTION_CANCEL
            -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}