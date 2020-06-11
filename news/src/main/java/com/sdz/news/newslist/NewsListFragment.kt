package com.sdz.news.newslist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.header.WaterDropHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.sdz.base.customview.BaseCustomViewModel
import com.sdz.base.fragment.MVVMFragment
import com.sdz.news.R
import com.sdz.news.databinding.FragmentNewsBinding

class NewsListFragment : MVVMFragment<FragmentNewsBinding, NewsListViewModel>(),NewsListViewModel.INewView {
    private var mAdapter: NewsListRecyclerViewAdapter? = null
    private var mChannelId = ""
    private var mChannelName = ""

    companion object{
        val BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id"
        val BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name"

        @JvmStatic
        fun newInstance(
            channelId: String?,
            channelName: String?
        ): NewsListFragment? {
            val fragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId)
            bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.attachUI(this)
        mFragmentTAG = "NewsListFragment"
        viewDataBinding.listview.setHasFixedSize(true)
        viewDataBinding.listview.layoutManager = LinearLayoutManager(context)
        mAdapter = NewsListRecyclerViewAdapter()
        viewDataBinding.listview.adapter = mAdapter
        viewDataBinding.refreshLayout.setRefreshHeader(WaterDropHeader(context))
        viewDataBinding.refreshLayout.setRefreshFooter(
            BallPulseFooter(requireContext()).setSpinnerStyle(
                SpinnerStyle.Scale
            )
        )
        viewDataBinding.refreshLayout.setOnRefreshListener { refreshlayout -> vm.tryToRefresh() }
        viewDataBinding.refreshLayout.setOnLoadMoreListener { refreshlayout -> vm.tryToLoadNextPage() }
        setLoadSir(viewDataBinding.refreshLayout)
        showLoading()
    }

    override fun getBindingVariable(): Int {
        return  0
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_news
    }

    override fun getViewModel(): NewsListViewModel {
        return NewsListViewModel(mChannelId,mChannelName)
    }

     override fun initParameters() {
        mChannelId = arguments?.getString(BUNDLE_KEY_PARAM_CHANNEL_ID)!!
        mChannelName = arguments?.getString(BUNDLE_KEY_PARAM_CHANNEL_NAME)!!
        mFragmentTAG = mChannelName
    }

    override fun onNewsLoaded(newsItems: ArrayList<BaseCustomViewModel>) {
        if (newsItems != null && newsItems.size > 0) {
            viewDataBinding.refreshLayout.finishLoadMore()
            viewDataBinding.refreshLayout.finishRefresh()
            showContent()
            mAdapter!!.setData(newsItems)
        } else {
            onRefreshEmpty()
        }
    }


    override fun onRetryBtnClick() {
        vm.tryToRefresh()
    }

     override fun getFragmentTag(): String? {
        return mChannelName
    }

}