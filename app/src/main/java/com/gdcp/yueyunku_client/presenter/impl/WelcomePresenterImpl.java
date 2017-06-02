package com.gdcp.yueyunku_client.presenter.impl;

import com.chyrta.onboarder.OnboarderPage;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.presenter.WelcomePresenter;
import com.gdcp.yueyunku_client.view.WelcomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 2017/5/10.
 */

public class WelcomePresenterImpl implements WelcomePresenter{
    private WelcomeView mView;
    public WelcomePresenterImpl(WelcomeView view){
        mView=view;
    }

    @Override
    public void setConfig() {
        OnboarderPage onboarderPage1 = new OnboarderPage("在这里你不是一个人在战斗", "Here you are not alone in the struggle",R.mipmap.sport1);
        OnboarderPage onboarderPage2 = new OnboarderPage("在这里你会发现更好的自己", "Here you will be beter and better",R.mipmap.sport4);
        OnboarderPage onboarderPage3 = new OnboarderPage("在这里你会收获真挚的友谊", "Here you will reap the true friendship",R.mipmap.sport3);

        onboarderPage1.setBackgroundColor(R.color.onboarder_bg_1);
        onboarderPage2.setBackgroundColor(R.color.onboarder_bg_2);
        onboarderPage3.setBackgroundColor(R.color.onboarder_bg_3);

        List<OnboarderPage> pages = new ArrayList<>();

        pages.add(onboarderPage1);
        pages.add(onboarderPage2);
        pages.add(onboarderPage3);

        for (OnboarderPage page : pages) {
            page.setTitleColor(R.color.primary_text);
            page.setDescriptionColor(R.color.secondary_text);
        }
        mView.onConfigSetted(pages);
    }
}
