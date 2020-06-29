package com.yurry.discovertmdb.model

import com.google.gson.annotations.SerializedName

data class APIError(
    @SerializedName("status_code") val code: Int,
    @SerializedName("status_message") val message: String
)