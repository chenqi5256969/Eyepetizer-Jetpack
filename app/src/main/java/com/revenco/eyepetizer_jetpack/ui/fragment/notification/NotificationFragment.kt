package com.revenco.eyepetizer_jetpack.ui.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.databinding.FragmentNotificationBinding


class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNotificationBinding>(
            inflater,
            R.layout.fragment_notification,
            container,
            false
        )
        return binding.root
    }
}