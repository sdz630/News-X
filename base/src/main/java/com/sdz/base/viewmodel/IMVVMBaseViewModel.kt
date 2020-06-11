package com.sdz.base.viewmodel

interface IMVVMBaseViewModel<V> {
    fun attachUI(view: V?)

    fun getPageView(): V?

    fun isUIAttached(): Boolean

    fun detachUI()
}
