package com.DefaultCompany.glaucoma_perimetry_system.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.controllers.ChoiceController;

public class ChoiceFragment extends Fragment implements View.OnClickListener {
    private TextView textHelp;
    private View choiceLayout;
    private ChoiceController choiceController;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        choiceLayout  = inflater.inflate(R.layout.fragment_choice, container, false);


        return choiceLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button leftEye = choiceLayout.findViewById(R.id.leftEye);
        Button rightEye = choiceLayout.findViewById(R.id.rightEye);
        textHelp = choiceLayout.findViewById(R.id.textHelp);
        leftEye.setOnClickListener(this);
        rightEye.setOnClickListener(this);
        choiceController=new ChoiceController(getActivity());
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
       if (choiceController.ifExistAir()) {
            startActivity(i);
        }else {
           textHelp.append("，未检测到耳机插入，请先插入耳机");
       }

    }



}
