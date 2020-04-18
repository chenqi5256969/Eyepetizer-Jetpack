package com.revenco.eyepetizer_jetpack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendItemBinding
import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp


class IndexRecommendRecyclerAdapter :
    PagedListAdapter<HomeDataResp.Issue.Item, IndexRecommendRecyclerAdapter.ViewHolder>(HomeDataRespDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<FragmentIndexRecommendItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.fragment_index_recommend_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!.data)
    }


    class ViewHolder constructor(private val binding: FragmentIndexRecommendItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setTextClickListener { v: View? ->
                v!!.findViewById<ViewPager2>(R.id.viewPager2).currentItem = FIND_FRAGMENT_INDEX
            }
        }

        fun bind(data: HomeDataResp.Issue.Item.Data) {
            with(binding) {
                resp = data
                executePendingBindings()
            }

        }
    }

    class HomeDataRespDiffCallback : DiffUtil.ItemCallback<HomeDataResp.Issue.Item>() {
        override fun areItemsTheSame(
            oldItem: HomeDataResp.Issue.Item,
            newItem: HomeDataResp.Issue.Item
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomeDataResp.Issue.Item,
            newItem: HomeDataResp.Issue.Item
        ): Boolean {
            return oldItem.id==newItem.id
        }


    }
}