package com.sdz.news.headline;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sdz.news.newslist.NewsListFragment;

import java.util.ArrayList;

public class HeadlineNewsFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<ChannelsModel.Channel> mChannels;

    public HeadlineNewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setChannels(ArrayList<ChannelsModel.Channel> channels){
        this.mChannels = channels;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int pos) {
        return NewsListFragment.newInstance(mChannels.get(pos).channelId, mChannels.get(pos).channelName);
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentPagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        if(mChannels != null && mChannels.size() > 0) {
            return mChannels.size();
        }
        return 0;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader){
    }
}