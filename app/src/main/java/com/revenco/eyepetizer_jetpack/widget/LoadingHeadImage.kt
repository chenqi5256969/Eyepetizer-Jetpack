package com.revenco.eyepetizer_jetpack.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.widget.ImageView
import com.revenco.eyepetizer_jetpack.R


class LoadingHeadImage @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : ImageView(context, attributeSet, def),
    ValueAnimator.AnimatorUpdateListener,
    Animatable {

    private var valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 360f)

    init {
        setImageResource(R.drawable.ic_app)
        setBackgroundColor(Color.BLUE)
        valueAnimator.apply {
            duration = 1000
            interpolator = null
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
    }

    override fun isRunning(): Boolean {
        return valueAnimator.isRunning
    }

    override fun start() {
        if (!valueAnimator.isRunning) {
            valueAnimator.addUpdateListener(this)
            valueAnimator.start()
        }
    }

    override fun stop() {
        if (valueAnimator.isRunning) {
            valueAnimator.removeAllListeners()
            valueAnimator.removeAllUpdateListeners()
            valueAnimator.cancel()
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        rotation = animation!!.animatedValue as Float
    }
}