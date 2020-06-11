package com.sdz.news.newslist

import com.sdz.base.customview.BaseCustomViewModel
import com.sdz.base.fragment.IBasePagingView
import com.sdz.base.model.BasePagingModel
import com.sdz.base.viewmodel.MVVMBaseViewModel
import com.sdz.common.views.picturetitleview.PictureTitleViewViewModel

class NewsListViewModel : MVVMBaseViewModel<NewsListViewModel.INewView, NewsListModel<*>>,
    BasePagingModel.IModelListener<ArrayList<BaseCustomViewModel>> {

    private var mNewsList = ArrayList<BaseCustomViewModel>()

    constructor(classId: String, lboClassId: String) {
        model = NewsListModel<ArrayList<PictureTitleViewViewModel>>(classId, lboClassId)
        model.register(this)
        model.getCachedDataAndLoad()
    }

    interface INewView : IBasePagingView {
        fun onNewsLoaded(channels: ArrayList<BaseCustomViewModel>)
    }

    override fun onLoadFinish(
        model: BasePagingModel<*>?,
        data: ArrayList<BaseCustomViewModel>?,
        isEmpty: Boolean,
        isFirstPage: Boolean,
        hasNextPage: Boolean
    ) {
        if (getPageView() != null) {
            if (model is NewsListModel) {
                if (isFirstPage) {
                    mNewsList.clear()
                }
                if (isEmpty) {
                    if (isFirstPage) {
                        getPageView()!!.onRefreshEmpty()
                    } else {
                        getPageView()!!.onLoadMoreEmpty()
                    }
                } else {
                    mNewsList.addAll(data!!)
                    getPageView()!!.onNewsLoaded(mNewsList)
                }
            }
        }
    }

    override fun onLoadFail(model: BasePagingModel<*>?, prompt: String?, isFirstPage: Boolean) {
        if (getPageView() != null) {
            if (isFirstPage) {
                if (prompt != null) {
                    getPageView()?.onRefreshFailure(prompt)
                }
            } else {
                if (prompt != null) {
                    getPageView()?.onLoadMoreFailure(prompt)
                }
            }
        }
    }
    fun tryToRefresh() {
        model?.refresh()
    }

    fun tryToLoadNextPage() {
        model?.loadNexPage()
    }


}