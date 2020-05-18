package com.revenco.eyepetizer_jetpack.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revenco.eyepetizer_jetpack.Main2Activity
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    //随机出现壁纸
    private var wallpaper: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this)
        setContentView(R.layout.activity_splash)
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
                 val intent = Intent(this@SplashActivity, Main2Activity::class.java)
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
