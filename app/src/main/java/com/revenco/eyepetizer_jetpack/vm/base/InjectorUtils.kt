package com.revenco.eyepetizer_jetpack.vm.base

import com.revenco.eyepetizer_jetpack.vm.factory.HomeViewModelFactory


object InjectorUtils {

    fun providerHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory()
    }
}