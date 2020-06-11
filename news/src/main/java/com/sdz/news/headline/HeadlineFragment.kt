package com.sdz.news.headline

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.sdz.base.fragment.MVVMFragment
import com.sdz.news.R
import com.sdz.news.databinding.FragmentHeadlineBinding
import java.util.*

class HeadlineFragment : MVVMFragment<FragmentHeadlineBinding, HeadlineViewModel>(),HeadlineViewModel.IMainView {
    var mAdapter: HeadlineNewsFragmentAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.attachUI(this)
        vm.refresh()
        viewDataBinding.tablayout.tabMode = TabLayout.MODE_SCROLLABLE
        initChannels()
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_headline
    }

    override fun getViewModel(): HeadlineViewModel {
        return HeadlineViewModel()
    }

    override fun onRetryBtnClick() {}


    override fun getFragmentTag(): String? {
        return "HeadlineNewsFragment"
    }

    fun initChannels() {
        mAdapter = HeadlineNewsFragmentAdapter(childFragmentManager)
        viewDataBinding.viewpager.adapter = mAdapter
        viewDataBinding.viewpager.addOnPageChangeListener(
            TabLayoutOnPageChangeListener(
                viewDataBinding.tablayout
            )
        )
        viewDataBinding.viewpager.offscreenPageLimit = 1
        //绑定tab点击事件
        viewDataBinding.tablayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewDataBinding.viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onChannelsLoaded(channels: ArrayList<ChannelsModel.Channel>) {
        mAdapter!!.setChannels(channels)
        viewDataBinding.tablayout.removeAllTabs()
        for (channel in channels) {
            viewDataBinding.tablayout.addTab(
                viewDataBinding.tablayout.newTab().setText(channel.channelName)
            )
        }
        viewDataBinding.tablayout.scrollTo(0, 0)
    }






}