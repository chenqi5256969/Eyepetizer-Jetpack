package com.revenco.eyepetizer_jetpack.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.revenco.eyepetizer_jetpack.utils.SizeUtils


class CircleProgress @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : View(context, attributeSet, def) {

    private var outLinePaint: Paint? = null
    private var inLinePaint: Paint? = null
    var textPaint: Paint? = null

    //内圈与外圈之间的padding
    val padding: Int = SizeUtils.px2dp(5f)

    //外圈半径
    val circleRadius: Int = SizeUtils.px2dp(100f)

    //扫描的角都
    var swipeAngle: Float = 0f

    //绘制的文字大小
    var canvasTextSize = SizeUtils.px2dp(25f).toFloat()

    init {
        outLinePaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.parseColor("#ffffff")
        }

        inLinePaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.parseColor("#ffffff")
        }

        textPaint = Paint().apply {
            color = Color.parseColor("#ffffff")
            textSize = canvasTextSize
            textSkewX = 0f
        }
    }

    fun startAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 360f)
        valueAnimator.addUpdateListener { animation ->
            swipeAngle = animation.animatedValue as Float
            invalidate()
        }
        valueAnimator.duration = 3000
        valueAnimator.start()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        //外圈
        canvas!!.drawCircle(
            (measuredWidth / 2).toFloat(),
            (measuredHeight / 2).toFloat(), circleRadius.toFloat(), outLinePaint!!
        )

        val rectF = RectF(
            (2 * padding).toFloat(),
            (2 * padding).toFloat(),
            measuredWidth.toFloat() - 2 * padding,
            measuredHeight.toFloat() - 2 * padding
        )
        //内圈
        //  canvas.drawArc(rectF, -90f, swipeAngle, true, inLinePaint!!)


        canvas.save()
        canvas.translate(
            (measuredWidth / 2).toFloat(),
            (measuredHeight / 2).toFloat()
        )

        //绘制文字
        val text = "2s"
        val textWidth = textPaint!!.measureText(text)
        val textHeight = textPaint!!.ascent() + textPaint!!.descent()

        canvas.drawText(text, -textWidth / 2, -textHeight / 2, textPaint!!)

        canvas.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(circleRadius * 2, circleRadius * 2)
    }
}