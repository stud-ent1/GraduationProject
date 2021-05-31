package com.DefaultCompany.glaucoma_perimetry_system.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.LoginComponent;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.LoginCompontent2;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.MainComponent;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.MainComponent2;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.unity3d.player.UnityPlayer;


public class MainActivity extends Activity implements View.OnClickListener {
    private View choiceLayout;
    private View resultLayout;
    private GlobalVal globalVal;
    private ChoiceFragment choiceFragment;
    private ResultFragment resultFragment;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalVal = (GlobalVal) getApplication();
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */

    private void initViews() {
        choiceLayout = findViewById(R.id.choice_layout);
        resultLayout = findViewById(R.id.result_layout);
        if (globalVal.isIfFirstToSee()) {
            choiceLayout.post(new Runnable() {
                @Override
                public void run() {
                    showGuideView();
                }
            });
            globalVal.setIfFirstToSee(false);
        }
        choiceLayout.setOnClickListener(this);
        resultLayout.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choice_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.result_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:

                choiceLayout.setBackgroundColor(0xffECE2EB);
                if (choiceFragment == null) {

                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    choiceFragment = new ChoiceFragment();
                    transaction.add(R.id.content, choiceFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(choiceFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                resultLayout.setBackgroundColor(0xffECE2EB);
                if (resultFragment == null) {

                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    resultFragment = new ResultFragment();
                    transaction.add(R.id.content, resultFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(resultFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (choiceFragment != null) {
            transaction.hide(choiceFragment);
        }
        if (resultFragment != null) {
            transaction.hide(resultFragment);
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        choiceLayout.setBackgroundColor(0xffffffff);
        resultLayout.setBackgroundColor(0xffffffff);
    }

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(choiceLayout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView2();
            }
        });

        builder.addComponent(new MainComponent());
        Guide guide = builder.createGuide();
        guide.show(MainActivity.this);
    }

    public void showGuideView2() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(resultLayout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                choiceFragment.showGuideView();
            }
        });

        builder1.addComponent(new MainComponent2());
        Guide guide = builder1.createGuide();
        guide.show(MainActivity.this);
    }

}
