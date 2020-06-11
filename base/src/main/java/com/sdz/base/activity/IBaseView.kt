package com.sdz.base.activity

interface IBaseView {
    fun showContent()

    fun showLoading()

    fun onRefreshEmpty()

    fun onRefreshFailure( message:String)
}