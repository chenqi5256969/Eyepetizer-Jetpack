package com.revenco.eyepetizer_jetpack.ui.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.databinding.FragmentCommunityBinding

/**
 * 社区
 */
class CommunityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCommunityBinding>(
            inflater,
            R.layout.fragment_community,
            container,
            false
        )
        return binding.root
    }
}