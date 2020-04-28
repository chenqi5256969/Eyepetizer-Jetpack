package com.revenco.eyepetizer_jetpack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.config.GlideApp
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendItemBinding
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendTextItemBinding
import com.revenco.eyepetizer_jetpack.net.bean.resp.HomeDataResp
import com.revenco.eyepetizer_jetpack.utils.GlideUtils
import com.revenco.eyepetizer_jetpack.utils.TimeUtils

class IndexRecommendRecyclerAdapter constructor(val viewPager2: ViewPager2) :
    PagedListAdapter<HomeDataResp.Issue.Item, RecyclerView.ViewHolder>(HomeDataRespDiffCallback()) {

    private val textItem = 0
    private val imageItem = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == imageItem) {
            val binding = DataBindingUtil.inflate<FragmentIndexRecommendItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.fragment_index_recommend_item,
                parent,
                false
            )
            return ImageViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<FragmentIndexRecommendTextItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.fragment_index_recommend_text_item,
                parent,
                false
            )
            return TextViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bind(getItem(position - 1)!!.data)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            textItem
        } else {
            imageItem
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }


    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(AdapterDataObserverProxy(1, observer))
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is ImageViewHolder) {
            holder.unBind()
        }
    }

    class ImageViewHolder constructor(
        private val binding: FragmentIndexRecommendItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val userIconImageView = binding.userIcon
        private val bigImageView = binding.bigImage

        init {
            binding.timeUtils = TimeUtils()
            binding.setTextClickListener {
            }
        }

        fun bind(data: HomeDataResp.Issue.Item.Data) {
            val bigImageUrl = data?.cover?.feed
            GlideUtils.loadImageNormal(bigImageView.context, bigImageUrl, bigImageView)
            val userIconUrl = data?.author?.icon
            GlideUtils.loadCircleImage(userIconImageView.context, userIconUrl, userIconImageView)
            binding.data = data
        }

        fun unBind() {
            GlideApp.with(bigImageView.context).clear(bigImageView)
            GlideApp.with(userIconImageView.context).clear(userIconImageView)
        }
    }

    inner class TextViewHolder constructor(binding: FragmentIndexRecommendTextItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setMoreClick {
                viewPager2.currentItem = DAILY_FRAGMENT_INDEX
            }
        }
    }

    class HomeDataRespDiffCallback : DiffUtil.ItemCallback<HomeDataResp.Issue.Item>() {
        override fun areItemsTheSame(
            oldItem: HomeDataResp.Issue.Item,
            newItem: HomeDataResp.Issue.Item
        ): Boolean {
            return oldItem.data.id == newItem.data.id
        }

        override fun areContentsTheSame(
            oldItem: HomeDataResp.Issue.Item,
            newItem: HomeDataResp.Issue.Item
        ): Boolean {
            return oldItem.data.id == newItem.data.id
        }
    }
}