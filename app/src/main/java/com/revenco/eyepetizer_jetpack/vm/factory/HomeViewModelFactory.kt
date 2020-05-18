package com.revenco.eyepetizer_jetpack.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revenco.eyepetizer_jetpack.vm.index.IndexRecommendViewModel


class HomeViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IndexRecommendViewModel() as T
    }
}