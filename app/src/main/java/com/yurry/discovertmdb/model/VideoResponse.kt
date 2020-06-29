package com.yurry.discovertmdb.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<Video>

)