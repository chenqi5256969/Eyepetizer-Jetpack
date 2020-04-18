package com.revenco.eyepetizer_jetpack.net.bean.resp

data class HomeDataResp(
    val issueList: List<Issue>,
    val nextPageUrl: String,
    val nextPublishTime: Long,
    val newestIssueType: String,
    val dialog: Any
) {
    data class Issue(
        val releaseTime: Long,
        val type: String,
        val date: Long,
        val publishTime: Long,
        val itemList: List<Item>,
        val count: Int
    ) {
        data class Item(
            val type: String,
            val `data`: Data,
            val id: Int,
            val adIndex: Int,
            val tag: Any
        ) {
            data class Data(
                val dataType: String,
                val id: Int,
                val title: String,
                val description: String,
                val library: String,
                val tags: List<Tag>,
                val consumption: Consumption,
                val resourceType: String,
                val slogan: Any,
                val provider: Provider,
                val category: String,
                val author: Author,
                val cover: Cover,
                val playUrl: String,
                val thumbPlayUrl: Any,
                val duration: Int,
                val webUrl: WebUrl,
                val releaseTime: Long,
                val playInfo: List<PlayInfo>,
                val campaign: Any,
                val waterMarks: Any,
                val ad: Boolean,
                val adTrack: List<Any>,
                val type: String,
                val titlePgc: Any,
                val descriptionPgc: Any,
                val remark: Any,
                val ifLimitVideo: Boolean,
                val searchWeight: Int,
                val brandWebsiteInfo: Any,
                val idx: Int,
                val shareAdTrack: Any,
                val favoriteAdTrack: Any,
                val webAdTrack: Any,
                val date: Long,
                val promotion: Any,
                val label: Any,
                val labelList: List<Any>,
                val descriptionEditor: String,
                val collected: Boolean,
                val reallyCollected: Boolean,
                val played: Boolean,
                val subtitles: List<Any>,
                val lastViewTime: Any,
                val playlists: Any,
                val src: Any
            ) {
                data class Tag(
                    val id: Int,
                    val name: String,
                    val actionUrl: String,
                    val bgPicture: String,
                    val headerImage: String,
                    val tagRecType: String,
                    val haveReward: Boolean,
                    val ifNewest: Boolean,
                    val communityIndex: Int,
                    val desc: String,
                    val adTrack: Any,
                    val childTagList: Any,
                    val childTagIdList: Any,
                    val newestEndTime: Any
                )

                data class Consumption(
                    val collectionCount: Int,
                    val shareCount: Int,
                    val replyCount: Int,
                    val realCollectionCount: Int
                )

                data class Provider(
                    val name: String,
                    val alias: String,
                    val icon: String
                )

                data class Author(
                    val id: Int,
                    val icon: String,
                    val name: String,
                    val description: String,
                    val link: String,
                    val latestReleaseTime: Long,
                    val videoNum: Int,
                    val adTrack: Any,
                    val follow: Follow,
                    val shield: Shield,
                    val approvedNotReadyVideoCount: Int,
                    val ifPgc: Boolean,
                    val recSort: Int,
                    val expert: Boolean
                ) {
                    data class Follow(
                        val itemType: String,
                        val itemId: Int,
                        val followed: Boolean
                    )

                    data class Shield(
                        val itemType: String,
                        val itemId: Int,
                        val shielded: Boolean
                    )
                }

                data class Cover(
                    val feed: String,
                    val detail: String,
                    val blurred: String,
                    val sharing: Any,
                    val homepage: String
                )

                data class WebUrl(
                    val raw: String,
                    val forWeibo: String
                )

                data class PlayInfo(
                    val height: Int,
                    val width: Int,
                    val urlList: List<Url>,
                    val name: String,
                    val type: String,
                    val url: String
                ) {
                    data class Url(
                        val name: String,
                        val url: String,
                        val size: Int
                    )
                }
            }
        }
    }
}