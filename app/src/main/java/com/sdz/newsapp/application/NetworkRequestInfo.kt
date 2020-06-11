package com.sdz.newsapp.application

import com.sdz.network.INetworkRequestInfo
import com.sdz.newsapp.BuildConfig
import java.util.HashMap

open class NetworkRequestInfo : INetworkRequestInfo {

    val headerMap = HashMap<String,String>()

    init {
        headerMap.put("os","android")
        headerMap.put("versionName",BuildConfig.VERSION_NAME)
        headerMap.put("versionCode", BuildConfig.VERSION_CODE.toString())
        headerMap.put("applicationId",BuildConfig.APPLICATION_ID)

    }


    override fun getRequestHeaderMap(): HashMap<String, String> {
        return headerMap
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}