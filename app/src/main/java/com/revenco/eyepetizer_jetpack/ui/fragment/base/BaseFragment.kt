package com.revenco.eyepetizer_jetpack.ui.fragment.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    private var mProgressDialog: ProgressDialog? = null
    val mViewModel: VM by lazy {
        providerVM()
    }
    lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, generateLayout(), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        startObserver()
    }

    abstract fun providerVM(): VM

    abstract fun initView()

    abstract fun startObserver()

    abstract fun generateLayout(): Int

    open fun showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity as Context?)
        }
        mProgressDialog!!.show()
    }

    open fun dismissProgress() {
        mProgressDialog!!.dismiss()
    }
}