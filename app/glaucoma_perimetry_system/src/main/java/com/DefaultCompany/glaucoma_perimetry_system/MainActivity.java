package com.DefaultCompany.glaucoma_perimetry_system;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener {
    private View choiceLayout;
    private View resultLayout;
    private ChoiceFragment choiceFragment;
    private ResultFragment resultFragment;
    private Intent intent;
    private GlobalVal globalVal;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        intent = getIntent();
        globalVal = (GlobalVal) getApplication();
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
        choiceLayout.setOnClickListener(this);
        resultLayout.setOnClickListener(this);
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

                choiceLayout.setBackgroundColor(0xff0000ff);
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
                resultLayout.setBackgroundColor(0xff0000ff);
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


}
