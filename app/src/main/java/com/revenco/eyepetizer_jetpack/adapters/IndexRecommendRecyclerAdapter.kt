package com.revenco.eyepetizer_jetpack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.revenco.eyepetizer_jetpack.R
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendBannerItemBinding
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendFollowCardItemBinding
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendItemBinding
import com.revenco.eyepetizer_jetpack.databinding.FragmentIndexRecommendTextItemBinding
import com.revenco.eyepetizer_jetpack.net.bean.resp.IndexRecommendBannerResp
import com.revenco.eyepetizer_jetpack.net.bean.resp.IndexRecommendFollowCardResp
import com.revenco.eyepetizer_jetpack.utils.GlideUtils
import org.json.JSONArray
import org.json.JSONObject

class IndexRecommendRecyclerAdapter constructor(json: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val headItem = 1

    //banner 样式
    private val bannerItem = 2
    private val squareCardCollection = "squareCardCollection"

    //banner 样式2
    private val banner2Item = 3
    private val banner2 = "banner2"

    //瀑布流样式
    private val followCardItem = 4
    private val followCard = "followCard"


    private var count = 0

    private lateinit var indexRecommendBannerResp: IndexRecommendBannerResp

    private var banner2ImageUrl = ""
    private var banner2Text = ""

    private lateinit var indexRecommendFollowCardResp: IndexRecommendFollowCardResp

    private var jsonArray: JSONArray

    init {
        //处理json
        val gson = Gson()
        val json = JSONObject(json)
        jsonArray = json.getJSONArray("itemList")
        count = jsonArray.length()

        for (index in 0 until 4) {
            val jsonObject = jsonArray.getJSONObject(index)
            when (jsonObject.getString("type")) {
                squareCardCollection -> {
                    indexRecommendBannerResp = gson.fromJson<IndexRecommendBannerResp>(
                        jsonObject.toString(),
                        IndexRecommendBannerResp::class.java
                    )
                }
                banner2 -> {
                    banner2ImageUrl = jsonObject.getJSONObject("data").getString("image")
                    banner2Text = "广告"
                }

                followCard -> {
                    indexRecommendFollowCardResp = gson.fromJson<IndexRecommendFollowCardResp>(
                        jsonObject.toString(),
                        IndexRecommendFollowCardResp::class.java
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            bannerItem -> return BannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.fragment_index_recommend_banner_item,
                    parent,
                    false
                )
            )
            banner2Item -> {
                return Banner2ViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.fragment_index_recommend_text_item,
                        parent,
                        false
                    )
                )
            }
            followCardItem -> return FollowCardViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.fragment_index_recommend_follow_card_item,
                    parent,
                    false
                )
            )
            else -> {
                return Banner2ViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.fragment_index_recommend_text_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        val jsonObject = jsonArray.getJSONObject(position)
        return when (jsonObject.getString("type")) {
            squareCardCollection -> bannerItem
            banner2 -> banner2Item
            followCard -> followCardItem
            else -> -1
        }
    }


    /**
     * 头部banner的item
     */
    inner class BannerViewHolder constructor(val binding: FragmentIndexRecommendBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recommendBanner.adapter = BannerViewHolderAdapter()
            PagerSnapHelper().attachToRecyclerView(binding.recommendBanner)
        }

        inner class BannerViewHolderAdapter :
            RecyclerView.Adapter<BannerViewHolderAdapter.BannerViewHolderAdapterViewHolder>() {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BannerViewHolderAdapterViewHolder {

                val itemBinding = DataBindingUtil.inflate<FragmentIndexRecommendItemBinding>(
                    LayoutInflater.from(
                        parent.context
                    ), R.layout.fragment_index_recommend_item, parent, false
                )
                return BannerViewHolderAdapterViewHolder(itemBinding)
            }

            override fun getItemCount(): Int {
                return indexRecommendBannerResp.data!!.itemList!!.size
            }

            override fun onBindViewHolder(
                holder: BannerViewHolderAdapterViewHolder,
                position: Int
            ) {
                val data = indexRecommendBannerResp
                    .data!!
                    .itemList!![position]!!
                    .data!!.content!!
                    .data!!

                holder.bind(
                    data.cover!!
                        .feed!!,
                    data.author!!.icon!!,
                    "${data.duration}",
                    data.title!!,
                    data.description!!
                )
            }

            inner class BannerViewHolderAdapterViewHolder constructor(val view: FragmentIndexRecommendItemBinding) :
                RecyclerView.ViewHolder(view.root) {

                fun bind(feed: String, icon: String, time: String, title: String, desc: String) {
                    GlideUtils.loadImageFitView(view.bigImage.context, feed, view.bigImage)
                    GlideUtils.loadImageFitView(view.userIcon.context, icon, view.userIcon)
                    view.timeShow.text = time
                    view.titleText.text = title
                    view.titleDesc.text = desc
                }
            }
        }
    }

    /**
     * 广告item
     */
    inner class Banner2ViewHolder constructor(val view: FragmentIndexRecommendTextItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        init {
            view.textView.text = banner2Text
            GlideUtils.loadImageFitView(
                view.banner2Image.context,
                banner2ImageUrl,
                view.banner2Image
            )
        }
    }


    /**
     * 瀑布流样式
     */
    inner class FollowCardViewHolder constructor(val binding: FragmentIndexRecommendFollowCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            /**
             * 展示大图
             */
            GlideUtils.loadImageNormal(
                binding.followImage.context,
                indexRecommendFollowCardResp.data!!.content!!.data!!.cover!!.feed!!,
                binding.followImage
            )
            binding.followTitle.text =
                (indexRecommendFollowCardResp.data!!.header!!.description!!)



            GlideUtils.loadCircleImage(
                binding.userIcon.context,
                indexRecommendFollowCardResp.data!!.header!!.icon!!,
                binding.userIcon
            )

            binding.titleText.text =
                (indexRecommendFollowCardResp.data!!.content!!.data!!.title!!)


            binding.titleDesc.text =
                (indexRecommendFollowCardResp.data!!.content!!.data!!.description!!)
        }
    }
}