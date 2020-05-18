package com.revenco.eyepetizer_jetpack.ui.fragment.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.revenco.eyepetizer_jetpack.EyepetizerApplication
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.utils.FileUtils
import com.revenco.eyepetizer_jetpack.vm.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_my.*
import java.io.File

class MyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val file = File(EyepetizerApplication.context.externalCacheDir, "cache")
        val size = FileUtils.getFileOrFilesSize(file.absolutePath, FileUtils.SIZETYPE_MB)
        lruText.text = "缓存大小$size"

        settingText.setOnClickListener {
            (activity!!.applicationContext as EyepetizerApplication).getAppViewModelProvider(activity!!)
                .get(BaseViewModel::class.java).statusBarColor.value=resources.getColor(R.color.colorAccent)
        }
    }

}
