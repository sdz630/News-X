package com.sdz.base.fragment

import com.sdz.base.activity.IBaseView

interface IBasePagingView :IBaseView{
    fun onLoadMoreFailure(message:String)
    fun onLoadMoreEmpty()
}