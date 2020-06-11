package com.sdz.news.headline

import com.google.gson.reflect.TypeToken
import com.sdz.base.model.BaseModel
import com.sdz.network.errorhandler.ExceptionHandle
import com.sdz.network.observer.BaseObserver
import com.sdz.news.api.NewsApi
import com.sdz.news.api.beans.NewsChannelsBean
import java.lang.reflect.Type

class ChannelsModel : BaseModel<ArrayList<ChannelsModel.Channel>>() {

    companion object {
        const val PREF_KEY_HOME_CHANNEL = "pref_key_home_channel"
        const val PREDEFINED_CHANNELS = "[\n" +
                "    {\n" +
                "        \"channelId\": \"5572a108b3cdc86cf39001cd\",\n" +
                "        \"channelName\": \"国内焦点\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"channelId\": \"5572a108b3cdc86cf39001ce\",\n" +
                "        \"channelName\": \"国际焦点\"\n" +
                "    }\n" +
                "]"
    }

    override fun getTClass(): Type {
        return object : TypeToken<ArrayList<Channel>>() {}.type
    }

    class Channel {
        lateinit var channelId: String
        lateinit var channelName: String
    }

    override fun refresh() {

    }

    override fun load() {
        NewsApi.getInstance().getNewsChannels(object : BaseObserver<NewsChannelsBean?>(this) {
            override fun onError(e: ExceptionHandle.ResponseThrowable) {
                e.printStackTrace()
                loadFail(e.msg)//这里不能使用message?
            }

            override fun onNext(newsChannelsBean: NewsChannelsBean) {
                val channels: java.util.ArrayList<Channel> =
                    java.util.ArrayList<Channel>()
                for (source in newsChannelsBean.showapiResBody.channelList) {
                    val channel: Channel = Channel()
                    channel.channelId = source.channelId
                    channel.channelName = source.name
                    channels.add(channel)
                }
                loadSuccess(channels)
            }
        })
    }


    override fun getCachedPreferenceKey(): String {
        return PREF_KEY_HOME_CHANNEL
    }

    override fun getApkString(): String {
        return PREDEFINED_CHANNELS
    }
}