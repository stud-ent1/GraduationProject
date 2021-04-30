package com.DefaultCompany.glaucoma_perimetry_system;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ChoiceFragment extends Fragment implements View.OnClickListener {
    private Button leftEye, rightEye;
    private TextView textHelp;
    private View choiceLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        choiceLayout  = inflater.inflate(R.layout.fragment_choice, container, false);


        return choiceLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        leftEye = choiceLayout.findViewById(R.id.leftEye);
        rightEye = choiceLayout.findViewById(R.id.rightEye);
        textHelp = choiceLayout.findViewById(R.id.textHelp);
        leftEye.setOnClickListener(this);
        rightEye.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 步骤1:通过getArgments()获取从Activity传过来的全部值
        Intent i = new Intent(getActivity(), UnityPlayerActivity.class); //切换窗口
        switch (v.getId()) {
            case R.id.leftEye:
                i.putExtra("eye","左眼");
                textHelp.setText("您选择的眼睛是左眼");
                break;
            case R.id.rightEye:
                i.putExtra("eye","右眼");
                textHelp.setText("您选择的眼睛是右眼");
                break;
            default:
                break;
        }
//        if (ifExistAir()) {

            startActivity(i);
//        }

    }

    /**
     * 判断是否有share
     */
//    private void ifHaveShare() {
//        if (share.getVisibility() == View.VISIBLE) {
//            textHelp.append("，如需分享，请点击上方分享按钮");
//        }
//    }

    /**
     * 设置所有组件隐藏
     */
    private void setControllerHide() {
        leftEye.setVisibility(View.INVISIBLE);
        rightEye.setVisibility(View.INVISIBLE);
        textHelp.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置share状态
     */
//    private void shareStatus(boolean status) {
//        if (status) {
//            share.setVisibility(View.VISIBLE);
//        } else {
//            share.setVisibility(View.INVISIBLE);
//        }
//    }

    /**
     * 检查耳机是否插入
     */
    private boolean ifExistAir() {
        AudioManager localAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        if (localAudioManager.isWiredHeadsetOn()) {
            textHelp.append("，检测到耳机插入，请按下耳机开始/暂停键，开始进行测试");
            return true;
        } else {
            textHelp.append("，未检测到耳机插入，请先插入耳机");
            return false;
        }
    }


}
