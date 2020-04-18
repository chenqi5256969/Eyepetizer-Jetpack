package com.revenco.eyepetizer_jetpack.ui.activity.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    private var mProgressDialog: ProgressDialog? = null
    val mViewModel: VM by lazy {
        providerVM()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(generateLayout())

    }

    abstract fun generateLayout(): Int

    abstract fun providerVM(): VM

    private fun showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
        }
        mProgressDialog!!.show()
    }

    private fun closeProgress() {
        mProgressDialog!!.dismiss()
    }
}
