package com.revenco.eyepetizer_jetpack.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle

class LoadingHeadView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : LinearLayout(context, attributeSet, def), RefreshHeader {
    var loadView: LoadingHeadImage

    init {
        gravity = Gravity.CENTER
        loadView = LoadingHeadImage(context)
        addView(loadView)
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        loadView.stop()//停止动画
        loadView.visibility = View.GONE//隐藏动画
        return 0
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {

    }

    override fun getView(): View {
        return this
    }

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        loadView.start()
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.PullDownToRefresh
            -> loadView.visibility = View.VISIBLE
        }
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }
}