package com.yurry.discovertmdb.rest

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yurry.discovertmdb.model.APIError
import retrofit2.Response


class APIErrorUtils {
    fun parseError(response: Response<*>): APIError? {
        val gson = Gson()
        val type = object : TypeToken<APIError>() {}.type
        return gson.fromJson(response.errorBody()!!.charStream(), type)
    }
}