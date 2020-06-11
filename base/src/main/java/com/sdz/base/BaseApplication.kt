package com.sdz.base

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.sdz.base.loadsir.*

open class BaseApplication : Application() {

    companion object {
        lateinit var sApplication: Application
        var sDebug: Boolean = false
    }

    fun setDebug(isDebug:Boolean){
        sDebug = isDebug
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback( LoadingCallback())
            .addCallback( TimeoutCallback())
            .addCallback( CustomCallback())
            .setDefaultCallback(LoadingCallback::class.java)//设置默认状态页
            .commit();
    }

}