package com.sdz.news.headline

import com.sdz.base.activity.IBaseView
import com.sdz.base.model.BaseModel
import com.sdz.base.viewmodel.MVVMBaseViewModel
import java.util.*
import kotlin.collections.ArrayList

class HeadlineViewModel : MVVMBaseViewModel<HeadlineViewModel.IMainView, ChannelsModel>() , BaseModel.IModelListener<ArrayList<ChannelsModel.Channel>>{
    var channels = ArrayList<ChannelsModel.Channel>()

    init {
        model = ChannelsModel()
        model?.register(this)
    }

    fun refresh() {
        model?.getCachedDataAndLoad()
    }

    override fun onLoadFinish(
        model: BaseModel<*>,
        data: ArrayList<ChannelsModel.Channel>?
    ) {
        if (model is ChannelsModel) {
            if (getPageView() != null && data is List<*>) {
                channels.clear()
                channels.addAll(data)
                getPageView()!!.onChannelsLoaded(channels)
            }
        }
    }



    interface IMainView : IBaseView {
        fun onChannelsLoaded(channels: ArrayList<ChannelsModel.Channel>)
    }

    override fun onLoadFail(model: BaseModel<*>?, prompt: String?) {
    }
}