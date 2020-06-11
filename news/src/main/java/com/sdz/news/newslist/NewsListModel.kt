package com.sdz.news.newslist

import com.google.gson.reflect.TypeToken
import com.sdz.base.customview.BaseCustomViewModel
import com.sdz.base.model.BasePagingModel
import com.sdz.common.views.picturetitleview.PictureTitleViewViewModel
import com.sdz.common.views.titleview.TitleViewViewModel
import com.sdz.network.errorhandler.ExceptionHandle
import com.sdz.network.observer.BaseObserver
import com.sdz.news.api.NewsApi
import com.sdz.news.api.beans.NewsListBean
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class NewsListModel<T>(var mChannelId: String = "",var mChannelName: String = "") : BasePagingModel<T>() {


    companion object {
        const val PREF_KEY_NEWS_CHANNEL = "pref_key_news_"
    }

    override fun getCachedPreferenceKey(): String {
        return PREF_KEY_NEWS_CHANNEL + mChannelId
    }

    override fun getTClass(): Type {
        return object :TypeToken<ArrayList<PictureTitleViewViewModel>>(){}.type
    }

    override fun refresh() {
        isRefresh = true
        load()
    }

    fun loadNexPage() {
        isRefresh = false
        load()
    }


    override fun load() {
        NewsApi.getInstance().getNewsList(object : BaseObserver<NewsListBean?>(this) {
            override fun onError(e: ExceptionHandle.ResponseThrowable) {
                e.printStackTrace()
                loadFail(e.message, isRefresh)
            }

            override fun onNext(newsChannelsBean: NewsListBean) {
                // All observer run on main thread, no need to synchronize
                pageNumber = if (isRefresh) 2 else pageNumber + 1
                val baseViewModels: ArrayList<BaseCustomViewModel> =
                    ArrayList<BaseCustomViewModel>()
                for (source in newsChannelsBean.showapiResBody.pagebean.contentlist) {
                    if (source.imageurls != null && source.imageurls.size > 1) {
                        val viewModel = PictureTitleViewViewModel()
                        viewModel.avatarUrl = source.imageurls.get(0).url
                        viewModel.link = source.link
                        viewModel.title = source.title
                        baseViewModels.add(viewModel)
                    } else {
                        val viewModel = TitleViewViewModel()
                        viewModel.link = source.link
                        viewModel.title = source.title
                        baseViewModels.add(viewModel)
                    }
                }
                loadSuccess(
                    baseViewModels as T,
                    baseViewModels.size == 0,
                    isRefresh,
                    baseViewModels.size == 0
                )
            }
        }, mChannelId, mChannelName, ""+(if (isRefresh) 1 else pageNumber))
    }


}