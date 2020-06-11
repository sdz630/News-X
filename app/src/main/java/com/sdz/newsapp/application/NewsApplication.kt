package com.sdz.newsapp.application

import com.billy.cc.core.component.CC
import com.kingja.loadsir.core.LoadSir
import com.sdz.base.BaseApplication
import com.sdz.base.loadsir.*
import com.sdz.base.preference.PreferencesUtil
import com.sdz.network.ApiBase
import com.sdz.newsapp.BuildConfig

class NewsApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        setDebug(BuildConfig.DEBUG)
        ApiBase.setNetworkRequestInfo(NetworkRequestInfo())

        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback( LoadingCallback())
            .addCallback( TimeoutCallback())
            .addCallback( CustomCallback())
            .setDefaultCallback(LoadingCallback::class.java)//设置默认状态页
            .commit();

        //CC
        CC.enableDebug(true)
        CC.enableVerboseLog(true)
        CC.enableRemoteCC(true)
    }


}