package com.rett.androidcouresfinalwork.utils;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;

public class MyVideoView extends JzvdStd {

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
        bottomProgressBar.setVisibility(GONE);
        loadingProgressBar.setVisibility(GONE);
        bottomProgressBar.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
    }


    @Override
    public void onClickUiToggle() {//点击时的UI变化
        super.onClickUiToggle();
        startButton.performClick();
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
    }
}
