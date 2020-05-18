package com.revenco.eyepetizer_jetpack.vm.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revenco.eyepetizer_jetpack.EyepetizerApplication

open class BaseViewModel : ViewModel(), LifecycleObserver {
    val statusBarColor = MutableLiveData<Int>()

    init {
        statusBarColor.value =
            EyepetizerApplication.context.resources.getColor(android.R.color.holo_orange_dark)
    }
}