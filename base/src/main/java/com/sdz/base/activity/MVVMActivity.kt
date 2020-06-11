package com.sdz.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sdz.base.loadsir.EmptyCallback
import com.sdz.base.loadsir.ErrorCallback
import com.sdz.base.loadsir.LoadingCallback
import com.sdz.base.viewmodel.IMVVMBaseViewModel
import com.sdz.base.viewmodel.MVVMBaseViewModel


/**
 * MVVMActivity
 */
abstract class MVVMActivity<VM : IMVVMBaseViewModel<*>> : AppCompatActivity(), IBaseView {
    protected  var vm: VM? = null
    private lateinit var mLoadService: LoadService<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun onRetryBtnCall() {
        TODO("Not yet implemented")
    }

    open fun setLoadsir(view: View) {
        mLoadService = LoadSir.getDefault().register(view, object:Callback.OnReloadListener{
            override fun onReload(v: View?) {
                onRetryBtnCall()
            }
        }) as LoadService

    }

    override fun showContent() {
        mLoadService.showSuccess()
    }

    override fun showLoading() {
        mLoadService.showCallback(LoadingCallback::class.java)
    }

    override fun onRefreshEmpty() {
        mLoadService.showCallback(EmptyCallback::class.java)
    }

    override fun onRefreshFailure(message: String) {
        mLoadService.showCallback(ErrorCallback::class.java)
    }


    protected abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM?

    /**
     * 数据绑定
     */
    private fun performDataBinding() {
        setContentView(getLayoutId())
        this.vm = getViewModel()

    }

}