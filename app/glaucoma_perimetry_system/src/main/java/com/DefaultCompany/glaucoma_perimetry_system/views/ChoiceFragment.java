package com.DefaultCompany.glaucoma_perimetry_system.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.controllers.ChoiceDeal;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;

public class ChoiceFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private TextView textHelp, textHelp1;
    private View choiceLayout;
    private RadioGroup programsRadioGroup, eyesRadioGroup;
    private ChoiceDeal choiceController;
    private RadioButton r;
    private GlobalVal globalVal;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        choiceLayout = inflater.inflate(R.layout.fragment_choice, container, false);


        return choiceLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button beginCheck = choiceLayout.findViewById(R.id.beginCheck);
        programsRadioGroup = choiceLayout.findViewById(R.id.programsRadioGroup);
        eyesRadioGroup = choiceLayout.findViewById(R.id.eyesRadioGroup);
        textHelp = choiceLayout.findViewById(R.id.textHelp);
        textHelp1 = choiceLayout.findViewById(R.id.textHelp1);
        beginCheck.setOnClickListener(this);
        programsRadioGroup.setOnCheckedChangeListener(this);
        eyesRadioGroup.setOnCheckedChangeListener(this);
        choiceController = new ChoiceDeal(getActivity());
        globalVal=(GlobalVal) getActivity().getApplication();
    }

    @Override
    public void onClick(View v) {
        // 步骤1:通过getArgments()获取从Activity传过来的全部值

       Intent i= new Intent(getActivity(), UnityPlayerActivity.class); //切换窗口
        i.putExtra("eye", globalVal.getEye());
        i.putExtra("program", globalVal.getProgram());
//       if (choiceController.ifExistAir()) {
        startActivity(i);
//        }else {
//           textHelp.append("，未检测到耳机插入，请先插入耳机");
//       }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        r = (RadioButton) choiceLayout.findViewById(checkedId);
        switch (r.getId()) {
            case R.id.leftEye:
                globalVal.setEye((String) r.getText());
                textHelp1.setText("您选择的眼睛是左眼");
                break;
            case R.id.rightEye:
                globalVal.setEye((String) r.getText());
                textHelp1.setText("您选择的眼睛是右眼");
                break;
            case R.id.earlyRadioButton:
                globalVal.setProgram((String) r.getText());
                textHelp.setText("您选择的检测程序是24-2");
                break;
            case R.id.lateRadioButton:
                globalVal.setProgram((String) r.getText());
                textHelp.setText("您选择的检测程序是10-2");
                break;
            default:
                break;
        }
    }
}
