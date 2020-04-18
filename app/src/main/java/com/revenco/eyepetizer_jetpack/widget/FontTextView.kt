package com.revenco.eyepetizer_jetpack.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.revenco.eyepetizer_jetpack.R


class FontTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : TextView(context, attributeSet, def) {

    init {
        val attrs =
            context.obtainStyledAttributes(attributeSet, R.styleable.FontTextView)
        val regular = attrs.getBoolean(R.styleable.FontTextView_regular, false)
        val lobster = attrs.getBoolean(R.styleable.FontTextView_lobster, false)
        if (regular) {
            val typeface =
                Typeface.createFromAsset(context.assets, "FZLanTingHeiS-L-GB-Regular.TTF")
            setTypeface(typeface)
        }

        if (lobster) {
            val typeface =
                Typeface.createFromAsset(context.assets, "Lobster-1.4.otf")
            setTypeface(typeface)
        }
        attrs.recycle()
    }
}