package com.sdz.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sdz.base.R
import com.sdz.base.loadsir.EmptyCallback
import com.sdz.base.loadsir.ErrorCallback
import com.sdz.base.loadsir.LoadingCallback
import com.sdz.base.utils.ToastUtil
import com.sdz.base.viewmodel.IMVVMBaseViewModel

abstract class MVVMFragment<V : ViewDataBinding,VM:IMVVMBaseViewModel<*>> : Fragment(),IBasePagingView{
    protected lateinit var vm:VM
    protected lateinit var viewDataBinding:V
    protected var mFragmentTAG = ""
    private lateinit var mLoadService: LoadService<*>

    abstract fun getBindingVariable():Int;

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = getViewModel()
//        vm.attachUI(this)
        if(getBindingVariable()>0){
            viewDataBinding.setVariable(getBindingVariable(), vm)
            viewDataBinding.executePendingBindings()
        }
    }

    /**
     * 懒加载 初始化参数
     */
    protected open fun initParameters(){}

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onRefreshEmpty() {
        mLoadService.showCallback(EmptyCallback::class.java)
    }

    override fun onRefreshFailure(message: String) {
        if (!isShowedContent) {
            mLoadService.showCallback(ErrorCallback::class.java)
        } else {
            ToastUtil.show(context, message)
        }
    }

    override fun showLoading() {
        mLoadService.showCallback(LoadingCallback::class.java)
    }
    private var isShowedContent = false

    override fun showContent() {
        isShowedContent = true
        mLoadService.showSuccess()
    }
    protected abstract fun onRetryBtnClick()

    override fun onLoadMoreFailure(message: String) {
        ToastUtil.show(context, message)
    }

    override fun onLoadMoreEmpty() {
        ToastUtil.show(context, getString(R.string.no_more_data))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(getFragmentTag(), "$this: onActivityCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(getFragmentTag(), "$this: onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(getFragmentTag(), "$this: onDetach")
    }

    override fun onStop() {
        super.onStop()
        Log.d(getFragmentTag(), "$this: onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(getFragmentTag(), "$this: onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(getFragmentTag(), "$this: onResume")
    }

    override fun onDestroy() {
        Log.d(getFragmentTag(), "$this: onDestroy")
        super.onDestroy()
    }


    fun setLoadSir(view:View){
        mLoadService = LoadSir.getDefault().register(view, Callback.OnReloadListener {
                onRetryBtnClick()
        }) as LoadService
    }

    abstract fun getFragmentTag(): String?


}