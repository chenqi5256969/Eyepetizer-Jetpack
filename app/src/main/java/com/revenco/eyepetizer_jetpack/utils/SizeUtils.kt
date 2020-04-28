package com.revenco.eyepetizer_jetpack.utils

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup


class SizeUtils private constructor() {

    companion object {
        /**
         * dp 转px
         */
        fun dp2px(dpValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * px 转 dp
         */
        fun px2dp(pxValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * sp 转px
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * px 转 sp
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         *  在 onCreate 中获取视图的尺寸
         */
        fun forceGetViewSize(view: View, listener: OnGetSizeListener?) {
            view.post {
                listener?.onGetSize(view)
            }
        }

        /**
         * Return the width of view.
         *
         * @param view The view.
         * @return the width of view
         */
        fun getMeasuredWidth(view: View): Int {
            return measureView(view)[0]
        }

        /**
         * Return the height of view.
         *
         * @param view The view.
         * @return the height of view
         */
        fun getMeasuredHeight(view: View): Int {
            return measureView(view)[1]
        }

        /**
         * Measure the view.
         *
         * @param view The view.
         * @return arr[0]: view's width, arr[1]: view's height
         */
        fun measureView(view: View): IntArray {
            var lp = view.layoutParams
            if (lp == null) {
                lp = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp!!.width)
            val lpHeight = lp!!.height
            val heightSpec: Int
            if (lpHeight > 0) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
            } else {
                heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            }
            view.measure(widthSpec, heightSpec)
            return intArrayOf(view.getMeasuredWidth(), view.getMeasuredHeight())
        }

        interface OnGetSizeListener {
            fun onGetSize(view: View)
        }
    }
}