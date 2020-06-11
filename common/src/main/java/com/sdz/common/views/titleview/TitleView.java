package com.sdz.common.views.titleview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.sdz.base.customview.BaseCustomView;
import com.sdz.common.R;
import com.sdz.common.databinding.TitleViewBindingImpl;
import com.sdz.common.webview.WebActivity;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class TitleView extends BaseCustomView<TitleViewBindingImpl, TitleViewViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void setDataToView(TitleViewViewModel data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {
        WebActivity.startCommonWeb(view.getContext(), "", getViewModel().link);
    }
}
