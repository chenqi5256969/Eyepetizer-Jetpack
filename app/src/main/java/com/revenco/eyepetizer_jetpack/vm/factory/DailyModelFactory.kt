package com.revenco.eyepetizer_jetpack.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revenco.eyepetizer_jetpack.vm.index.IndexDailyViewModel


class DailyModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IndexDailyViewModel() as T
    }
}