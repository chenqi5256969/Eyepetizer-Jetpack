package com.revenco.eyepetizer_jetpack.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.revenco.eyepetizer_jetpack.MainActivity
import com.revenco.eyepetizer_jetpack.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    //随机出现壁纸
    var wallpaper: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
        wallpaper = (0..13).random()
        val resId = resources.getIdentifier("wallpaper_$wallpaper", "drawable", packageName)
        backImage.setImageResource(resId)

        val animatorScaleX =
            ObjectAnimator.ofObject(backImage, "scaleX", FloatEvaluator(), 1f, 1.2f)

        val animatorScaleY =
            ObjectAnimator.ofObject(backImage, "scaleY", FloatEvaluator(), 1f, 1.2f)
        val animatorSet = AnimatorSet()
        animatorSet.play(animatorScaleX).with(animatorScaleY)
        animatorSet.duration = 1500
        animatorSet.start()

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                //进入首页
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }
}
