package com.sdz.base.viewmodel

import androidx.lifecycle.ViewModel
import com.sdz.base.model.BaseCachedData
import com.sdz.base.model.SuperBaseModel
import java.lang.ref.Reference
import java.lang.ref.WeakReference

abstract class MVVMBaseViewModel<V,M: SuperBaseModel<*>>:ViewModel(),IMVVMBaseViewModel<V>{
    private var mUIRef: Reference<V?>? = null
    protected lateinit var model:M

    override fun attachUI(view: V?) {
        mUIRef = WeakReference(view)
    }

    override fun getPageView(): V? {
        return mUIRef?.get()
    }

    override fun isUIAttached(): Boolean {
        return mUIRef!=null&& mUIRef?.get()!=null;
    }

    override fun detachUI() {
        mUIRef?.clear()
        mUIRef = null
        model?.cancel()

    }

}